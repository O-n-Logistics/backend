package on.logistics.companyservice.application.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import on.logistics.companyservice.application.dtos.request.CreateCompanyRequestDto;
import on.logistics.companyservice.application.dtos.request.SearchCompanyRequestDto;
import on.logistics.companyservice.application.dtos.request.UpdateCompanyHubRequestDto;
import on.logistics.companyservice.application.dtos.request.UpdateCompanyRequestDto;
import on.logistics.companyservice.domain.entity.Company;
import on.logistics.companyservice.domain.entity.dtos.CreateCompanyDto;
import on.logistics.companyservice.domain.repository.CompanyRepository;
import on.logistics.companyservice.exception.CompanyException;
import on.logistics.companyservice.exception.CompanyExceptionCode;
import on.logistics.companyservice.global.application.dtos.PageDto;
import on.logistics.companyservice.presentation.dtos.response.CreateCompanyResponse;
import on.logistics.companyservice.presentation.dtos.response.GetCompanyResponse;
import on.logistics.companyservice.presentation.dtos.response.SearchCompanyResponse;
import on.logistics.companyservice.presentation.dtos.response.UpdateCompanyHubResponse;
import on.logistics.companyservice.presentation.dtos.response.UpdateCompanyResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    @Transactional
    public CreateCompanyResponse createCompany(CreateCompanyRequestDto requestDto) {
        // todo : 임시 유저 아이디 발급 로직 수정 필요
        UUID userId = UUID.randomUUID();

        companyRepository.findByUserId(userId).ifPresent(company -> {
            throw new CompanyException(CompanyExceptionCode.COMPANY_USER_ID_DUPLICATE);
        });

        CreateCompanyDto createCompanyDto = CreateCompanyDto.from(userId, requestDto.companyName(),
            requestDto.companyType(), requestDto.companyAddress());
        Company company = Company.create(createCompanyDto);
        Company saved = companyRepository.save(company);
        return CreateCompanyResponse.of(saved.getId());
    }

    @Override
    public PageDto<SearchCompanyResponse> searchCompany(SearchCompanyRequestDto requestDto) {
        Page<Company> companyPage = companyRepository.searchCompany(requestDto);
        Page<SearchCompanyResponse> responsePage = companyPage.map(
            SearchCompanyResponse::from);
        return PageDto.from(responsePage);
    }

    @Override
    public GetCompanyResponse getCompany(UUID id) {
        Company company = getOrElseThrow(id);
        return GetCompanyResponse.of(company.getId(), company.getName().getValue(),
            company.getType(), company.getManagedHubId(), company.getAddress().getValue());
    }

    @Override
    @Transactional
    public UpdateCompanyResponse updateCompany(UUID id, UpdateCompanyRequestDto requestDto) {
        // todo : 유저의 아이디 정보를 받아와서 본인 회사인지 체크하는 로직 필요
        Company company = getOrElseThrow(id);
        company.update(requestDto.companyName(), requestDto.companyAddress());
        return UpdateCompanyResponse.of(company.getId());
    }

    @Override
    @Transactional
    public void deleteCompany(UUID id) {
        // todo : 유저의 아이디 정보를 받아와서 본인 회사인지 체크하는 로직 필요
        Company company = getOrElseThrow(id);
        companyRepository.delete(company);
    }

    @Override
    @Transactional
    public UpdateCompanyHubResponse updateCompanyHub(UUID id,
        UpdateCompanyHubRequestDto requestDto) {
        // todo : 유저의 아이디 정보를 받아와서 본인 회사인지 체크하는 로직 필요
        Company company = getOrElseThrow(id);
        company.updateHub(requestDto.managedHubId());
        return UpdateCompanyHubResponse.of(company.getId());
    }

    private Company getOrElseThrow(UUID id) {
        return companyRepository.findById(id)
            .orElseThrow(() -> new CompanyException(CompanyExceptionCode.COMPANY_IS_NOT_FOUND));
    }
}