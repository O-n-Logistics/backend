package on.logistics.companyservice.presentation.dtos.response;

import java.util.UUID;
import on.logistics.companyservice.domain.entity.enums.CompanyType;

public record GetCompanyResponse(UUID companyId, String companyName, CompanyType companyType,
                                 UUID managedHubId, String companyAddress) {

    public static GetCompanyResponse of(UUID companyId, String companyName, CompanyType companyType,
        UUID managedHubId, String companyAddress) {
        return new GetCompanyResponse(companyId, companyName, companyType, managedHubId,
            companyAddress);
    }
}