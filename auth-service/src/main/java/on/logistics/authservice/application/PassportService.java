package on.logistics.authservice.application;

import java.util.UUID;
import on.logistics.authservice.infrastructure.security.passport.Passport;

public interface PassportService {

    Passport getPassportByPassportId(String passportId);

    String createAndStorePassport(String token, UUID userId);

    String getPassportIdByRefreshToken(String token);

    Passport getPassportByRefreshToken(String token);

    void expirePassportByRefreshToken(String refreshToken);

    void deletePassportByRefreshToken(String refreshToken);

    String createAndStoreNewPassportByRefreshToken(String refreshToken, String newRefreshToken);

    void deletePassportByPassportId(String passportId);
}
