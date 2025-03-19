package on.logistics.companyservice.presentation.dtos.response;

import java.util.List;

public record SearchCompanyListResponse(List<SearchCompanyResponse> content, boolean last,
                                        int totalPage, Long totalElements) {

    public static SearchCompanyListResponse of(List<SearchCompanyResponse> content, boolean last,
        int totalPage, Long totalElements) {
        return new SearchCompanyListResponse(content, last, totalPage, totalElements);
    }
}
