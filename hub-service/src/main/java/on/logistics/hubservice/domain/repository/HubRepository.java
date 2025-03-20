package on.logistics.hubservice.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import on.logistics.hubservice.application.dtos.request.SearchHubRequestDto;
import on.logistics.hubservice.domain.entity.Hub;
import on.logistics.hubservice.global.application.dtos.PageDto;
import on.logistics.hubservice.presentation.dtos.response.GetSpokesLinkedToCenterResponse;
import on.logistics.hubservice.presentation.dtos.response.SearchHubResponse;
import org.springframework.stereotype.Repository;

@Repository
public interface HubRepository {

    Hub save(Hub hub);

    boolean existsById(UUID id);

    Optional<Hub> findByIdAndIsDeleted(UUID id, boolean isDeleted);

    PageDto<SearchHubResponse> searchHub(SearchHubRequestDto requestDto);

    List<GetSpokesLinkedToCenterResponse> findSpokesLinkedToCenter(UUID centerId);
}
