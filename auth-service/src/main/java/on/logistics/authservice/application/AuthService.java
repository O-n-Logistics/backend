package on.logistics.authservice.application;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import on.logistics.authservice.application.dtos.AuthSignupRequestDto;
import on.logistics.authservice.presentation.dtos.AuthReissueTokensResponse;
import on.logistics.authservice.presentation.dtos.AuthSignupResponse;
import on.logistics.authservice.presentation.dtos.AuthValidateResponse;

public interface AuthService {

    AuthSignupResponse signup(AuthSignupRequestDto authRequestDto);

    AuthValidateResponse validate(HttpServletRequest request);

    void logout(HttpServletRequest request);

    AuthReissueTokensResponse reIssueTokens(HttpServletRequest request,
        HttpServletResponse response);

    void deleteAuthByPassport(HttpServletRequest request);

    void deleteAuthByUserId(String userId);
}
