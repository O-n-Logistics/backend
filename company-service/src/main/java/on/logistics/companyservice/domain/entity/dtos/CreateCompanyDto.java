package on.logistics.companyservice.domain.entity.dtos;

import java.util.UUID;
import on.logistics.companyservice.domain.entity.enums.CompanyType;


public record CreateCompanyDto(UUID userId, String companyName, CompanyType type,
                               String companyAddress) {

    public static CreateCompanyDto from(UUID userId, String companyName, CompanyType type,
        String companyAddress) {
        return new CreateCompanyDto(userId, companyName, type, companyAddress);
    }
}
