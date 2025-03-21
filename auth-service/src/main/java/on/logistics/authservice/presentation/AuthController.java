package on.logistics.authservice.presentation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import on.logistics.authservice.application.AuthServiceImpl;
import on.logistics.authservice.application.dtos.AuthSignupRequestDto;
import on.logistics.authservice.global.presentation.dtos.CommonResponse;
import on.logistics.authservice.presentation.dtos.AuthReissueTokensResponse;
import on.logistics.authservice.presentation.dtos.AuthSignupRequest;
import on.logistics.authservice.presentation.dtos.AuthSignupResponse;
import on.logistics.authservice.presentation.dtos.AuthValidateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<AuthSignupResponse>> userSignup(
        @RequestBody AuthSignupRequest request
    ) {
        log.info("user signup request: {}", request);

        AuthSignupRequestDto requestDto = AuthSignupRequestDto.from(request);
        AuthSignupResponse response = authService.signup(requestDto);
        return ResponseEntity.ok(CommonResponse.success(response));
    }

    @GetMapping("/validate")
    public ResponseEntity<CommonResponse<AuthValidateResponse>> validate(
        HttpServletRequest request
    ) {
        log.info("validate request: {}", request);

        AuthValidateResponse response = authService.validate(request);
        return ResponseEntity.ok(CommonResponse.success(response));
    }

    @PostMapping("/logout")
    public ResponseEntity<CommonResponse<Void>> logout(
        HttpServletRequest request
    ) {
        log.info("logout request: {}", request);

        authService.logout(request);
        return ResponseEntity.ok(CommonResponse.success());
    }

    @PostMapping("/refresh")
    public ResponseEntity<CommonResponse<AuthReissueTokensResponse>> reissueRefreshToken(
        HttpServletRequest servletRequest,
        HttpServletResponse servletResponse
    ) {
        log.info("reissue refresh token request: {}", servletRequest);

        AuthReissueTokensResponse response = authService.reIssueTokens(servletRequest,
            servletResponse);
        return ResponseEntity.ok(CommonResponse.success(response));
    }

    @DeleteMapping("/my")
    public ResponseEntity<CommonResponse<Void>> deleteAuthByPassport(
        HttpServletRequest request
    ) {
        log.info("delete auth by request: {}", request);

        authService.deleteAuthByPassportId(request);
        return ResponseEntity.ok(CommonResponse.success());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<CommonResponse<Void>> deleteAuthByUserId(
        @PathVariable String userId
    ) {
        log.info("delete auth by Id: {}", userId);

        authService.deleteAuthByUserId(userId);
        return ResponseEntity.ok(CommonResponse.success());
    }
}
