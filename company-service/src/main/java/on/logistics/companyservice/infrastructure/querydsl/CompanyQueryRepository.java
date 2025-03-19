package on.logistics.companyservice.infrastructure.querydsl;

import on.logistics.companyservice.application.dtos.request.SearchCompanyRequestDto;
import on.logistics.companyservice.domain.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyQueryRepository {

    Page<Company> searchCompany(SearchCompanyRequestDto requestDto);
}