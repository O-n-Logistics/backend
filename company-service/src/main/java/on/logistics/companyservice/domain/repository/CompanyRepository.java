package on.logistics.companyservice.domain.repository;

import java.util.Optional;
import java.util.UUID;
import on.logistics.companyservice.application.dtos.request.SearchCompanyRequestDto;
import on.logistics.companyservice.domain.entity.Company;
import org.springframework.data.domain.Page;

public interface CompanyRepository {

    Company save(Company company);

    void delete(Company company);

    Optional<Company> findById(UUID id);

    Optional<Company> findByUserId(UUID userId);

    Page<Company> searchCompany(SearchCompanyRequestDto requestDto);
}
