package on.logistics.companyservice.presentation.dtos.response;

import java.util.UUID;
import on.logistics.companyservice.domain.entity.Company;

public record SearchCompanyResponse(UUID companyId, UUID userId, String companyName,
                                    UUID managedHubId) {

    public static SearchCompanyResponse from(Company company) {
        return new SearchCompanyResponse(company.getId(), company.getUserId(),
            company.getName().getValue(), company.getManagedHubId());
    }

}