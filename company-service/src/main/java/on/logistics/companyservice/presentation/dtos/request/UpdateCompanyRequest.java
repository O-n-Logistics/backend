package on.logistics.companyservice.presentation.dtos.request;

import jakarta.validation.constraints.NotBlank;
import on.logistics.companyservice.application.dtos.request.UpdateCompanyRequestDto;

public record UpdateCompanyRequest(@NotBlank String companyName,
                                   @NotBlank String companyAddress) {

    public static UpdateCompanyRequestDto from(UpdateCompanyRequest dto) {
        return new UpdateCompanyRequestDto(
            dto.companyName(),
            dto.companyAddress());
    }
}