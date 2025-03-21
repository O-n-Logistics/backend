package on.logistics.authservice.presentation.dtos;

import lombok.Builder;
import on.logistics.authservice.application.dtos.AuthSignupResponseDto;
import on.logistics.authservice.domain.vo.Username;

@Builder
public record AuthSignupResponse(
    String username,
    String nickname
) {

    public static AuthSignupResponse from(AuthSignupResponseDto dto) {
        return new AuthSignupResponse(
            dto.username().toString(),
            dto.nickname()
        );
    }
}
