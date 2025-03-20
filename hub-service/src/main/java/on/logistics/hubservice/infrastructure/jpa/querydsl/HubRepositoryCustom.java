package on.logistics.hubservice.infrastructure.jpa.querydsl;

import java.util.List;
import java.util.UUID;
import on.logistics.hubservice.application.dtos.request.SearchHubRequestDto;
import on.logistics.hubservice.global.application.dtos.PageDto;
import on.logistics.hubservice.presentation.dtos.response.GetSpokesLinkedToCenterResponse;
import on.logistics.hubservice.presentation.dtos.response.SearchHubResponse;
import org.springframework.stereotype.Repository;

@Repository
public interface HubRepositoryCustom {

    PageDto<SearchHubResponse> searchHub(SearchHubRequestDto requestDto);

    List<GetSpokesLinkedToCenterResponse> getSpokesLinkedToCenter(UUID centerId);
}
