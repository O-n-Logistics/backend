package on.logistics.companyservice.presentation.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import on.logistics.companyservice.application.dtos.request.CreateCompanyRequestDto;
import on.logistics.companyservice.domain.entity.enums.CompanyType;


public record CreateCompanyRequest(
    @NotBlank(message = "업체 이름은 반드시 입력되어야 합니다.") @Size(min = 1, max = 100, message = "업체 이름은 1자 이하, 100자 이상 초과할 수 없습니다.") String companyName,
    @NotNull(message = "업체 타입은 반드시 입력되어야 합니다.") CompanyType companyType,
    @NotBlank(message = "업체 주소는 반드시 입력되어야 합니다.") String companyAddress) {

    public static CreateCompanyRequestDto from(CreateCompanyRequest dto) {
        return new CreateCompanyRequestDto(dto.companyName(), dto.companyType(),
            dto.companyAddress());
    }
}
