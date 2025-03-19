package on.logistics.companyservice.application.service;

import java.util.UUID;
import on.logistics.companyservice.application.dtos.request.CreateCompanyRequestDto;
import on.logistics.companyservice.application.dtos.request.SearchCompanyRequestDto;
import on.logistics.companyservice.application.dtos.request.UpdateCompanyHubRequestDto;
import on.logistics.companyservice.application.dtos.request.UpdateCompanyRequestDto;
import on.logistics.companyservice.global.application.dtos.PageDto;
import on.logistics.companyservice.presentation.dtos.response.CreateCompanyResponse;
import on.logistics.companyservice.presentation.dtos.response.GetCompanyResponse;
import on.logistics.companyservice.presentation.dtos.response.SearchCompanyResponse;
import on.logistics.companyservice.presentation.dtos.response.UpdateCompanyHubResponse;
import on.logistics.companyservice.presentation.dtos.response.UpdateCompanyResponse;

public interface CompanyService {

    CreateCompanyResponse createCompany(CreateCompanyRequestDto requestDto);

    PageDto<SearchCompanyResponse> searchCompany(SearchCompanyRequestDto requestDto);

    GetCompanyResponse getCompany(UUID id);

    UpdateCompanyResponse updateCompany(UUID id, UpdateCompanyRequestDto requestDto);

    void deleteCompany(UUID id);

    UpdateCompanyHubResponse updateCompanyHub(UUID id, UpdateCompanyHubRequestDto requestDto);
}
