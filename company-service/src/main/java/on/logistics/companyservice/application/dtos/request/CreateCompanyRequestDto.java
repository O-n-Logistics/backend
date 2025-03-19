package on.logistics.companyservice.application.dtos.request;

import on.logistics.companyservice.domain.entity.enums.CompanyType;

public record CreateCompanyRequestDto(String companyName,
                                      CompanyType companyType,
                                      String companyAddress) {

}
