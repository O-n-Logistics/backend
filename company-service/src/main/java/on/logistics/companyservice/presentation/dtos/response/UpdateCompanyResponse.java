package on.logistics.companyservice.presentation.dtos.response;

import java.util.UUID;

public record UpdateCompanyResponse(UUID companyId) {

    public static UpdateCompanyResponse of(UUID companyId) {
        return new UpdateCompanyResponse(companyId);
    }
}
