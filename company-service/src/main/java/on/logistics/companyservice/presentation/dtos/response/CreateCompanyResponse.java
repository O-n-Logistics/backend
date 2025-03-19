package on.logistics.companyservice.presentation.dtos.response;

import java.util.UUID;

public record CreateCompanyResponse(UUID companyId) {

    public static CreateCompanyResponse of(UUID companyId) {
        return new CreateCompanyResponse(companyId);
    }
}
