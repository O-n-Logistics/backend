package on.logistics.authservice.application;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import on.logistics.authservice.application.dtos.AuthSignupRequestDto;
import on.logistics.authservice.application.dtos.AuthSignupResponseDto;
import on.logistics.authservice.domain.entity.Auth;
import on.logistics.authservice.domain.repository.AuthRepository;
import on.logistics.authservice.enums.AuthRole;
import on.logistics.authservice.exception.AuthException;
import on.logistics.authservice.exception.AuthExceptionCode;
import on.logistics.authservice.infrastructure.feign.UserClientService;
import on.logistics.authservice.infrastructure.feign.dtos.UserCreateRequest;
import on.logistics.authservice.infrastructure.feign.dtos.UserCreateResponse;
import on.logistics.authservice.infrastructure.security.cookie.CookieUtil;
import on.logistics.authservice.infrastructure.security.jwt.JwtUtil;
import on.logistics.authservice.infrastructure.security.passport.Passport;
import on.logistics.authservice.presentation.dtos.AuthReissueTokensResponse;
import on.logistics.authservice.presentation.dtos.AuthSignupResponse;
import on.logistics.authservice.presentation.dtos.AuthValidateResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "AuthServiceImpl")
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserClientService userClientService;
    private final CookieUtil cookieUtil;
    private final PassportService passportService;
    private final JwtUtil jwtUtil;

    @Transactional
    public AuthSignupResponse signup(
        AuthSignupRequestDto authRequestDto
    ) {

        if (authRepository.existsByUsername(authRequestDto.username())) {
            throw new AuthException(AuthExceptionCode.AUTH_USERNAME_DUPLICATE);
        }

        UserCreateRequest userRequest = UserCreateRequest.from(authRequestDto);
        UserCreateResponse userResponse = userClientService.createUser(userRequest);

        String encodedPassword = passwordEncoder.encode(authRequestDto.password().toString());
        Auth auth = Auth.from(authRequestDto, userResponse, encodedPassword);

        auth.setId(userResponse.userId());
        Auth savedAuth = authRepository.save(auth);

        AuthSignupResponseDto authResponseDto = AuthSignupResponseDto.from(savedAuth, userResponse);

        return AuthSignupResponse.from(authResponseDto);
    }

    @Override
    public AuthValidateResponse validate(
        HttpServletRequest request
    ) {
        String refreshToken = cookieUtil.getRefreshTokenFromCookie(request);
        String passportId = passportService.getPassportIdByRefreshToken(refreshToken);

        return AuthValidateResponse.of(passportId);
    }

    @Override
    public void logout(
        HttpServletRequest request
    ) {
        String refreshToken = cookieUtil.getRefreshTokenFromCookie(request);
        passportService.deletePassportByRefreshToken(refreshToken);
    }

    @Override
    public AuthReissueTokensResponse reIssueTokens(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        String refreshToken = cookieUtil.getRefreshTokenFromCookie(request);

        Claims claims = jwtUtil.getUserInfoFromToken(refreshToken);
        String username = claims.getSubject();
        String roleClaim = claims.get("role", String.class);
        AuthRole role = AuthRole.valueOf(roleClaim);

        String reIssuedRefreshToken = jwtUtil.generateRefreshToken(username, role);
        String reIssuedAccessToken = jwtUtil.generateAccessToken(username, role);
        String passportId = passportService.createAndStoreNewPassportByRefreshToken(
            refreshToken,
            reIssuedRefreshToken
        );

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, reIssuedAccessToken);
        cookieUtil.addRefreshTokenToCookie(response, reIssuedRefreshToken);
        return AuthReissueTokensResponse.from(passportId);
    }

    @Override
    public void deleteAuthByPassport(
        HttpServletRequest request
    ) {
        String refreshToken = cookieUtil.getRefreshTokenFromCookie(request);
        Passport passport = passportService.getPassportByRefreshToken(refreshToken);
        Auth auth = authRepository.findById(passport.getUserId()).orElseThrow(
            () -> new AuthException(AuthExceptionCode.AUTH_IS_NOT_FOUND)
        );

        authRepository.delete(auth);
        passportService.deletePassportByRefreshToken(refreshToken);
    }

    public void deleteAuthByPassportId(
        HttpServletRequest request
    ) {
        String passportId = request.getHeader("X-Passport-Id");
        Passport passport = passportService.getPassportByPassportId(passportId);
        Auth auth = authRepository.findByUserId(passport.getUserId()).orElseThrow(
            () -> new AuthException(AuthExceptionCode.AUTH_IS_NOT_FOUND)
        );

        authRepository.delete(auth);
        passportService.deletePassportByPassportId(passportId);
    }

    @Override
    public void deleteAuthByUserId(
        String userId
    ) {
        Auth auth = authRepository.findByUserId(UUID.fromString(userId)).orElseThrow(
            () -> new AuthException(AuthExceptionCode.AUTH_IS_NOT_FOUND)
        );

        authRepository.delete(auth);
    }


}
