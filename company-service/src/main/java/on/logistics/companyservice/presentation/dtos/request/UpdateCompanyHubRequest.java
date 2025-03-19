package on.logistics.companyservice.presentation.dtos.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import on.logistics.companyservice.application.dtos.request.UpdateCompanyHubRequestDto;

public record UpdateCompanyHubRequest(@NotNull(message = "허브 ID는 필수 입력 조건입니다.") UUID managedHubId) {

    public static UpdateCompanyHubRequestDto from(UpdateCompanyHubRequest dto) {
        return new UpdateCompanyHubRequestDto(
            dto.managedHubId());
    }

}
