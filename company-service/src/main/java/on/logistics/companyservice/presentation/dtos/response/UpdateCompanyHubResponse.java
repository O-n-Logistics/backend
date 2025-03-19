package on.logistics.companyservice.presentation.dtos.response;

import java.util.UUID;

public record UpdateCompanyHubResponse(UUID companyId) {

    public static UpdateCompanyHubResponse of(UUID companyId) {
        return new UpdateCompanyHubResponse(companyId);
    }
}
