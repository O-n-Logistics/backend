package on.logistics.authservice.presentation.dtos;

public record AuthReissueTokensResponse(
    String passportId
) {

    public static AuthReissueTokensResponse from(String passportId) {
        return new AuthReissueTokensResponse(
            passportId
        );
    }
}
