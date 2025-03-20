package on.logistics.hubservice.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import on.logistics.hubservice.application.dtos.request.SearchHubRequestDto;
import on.logistics.hubservice.domain.entity.Hub;
import on.logistics.hubservice.global.application.dtos.PageDto;
import on.logistics.hubservice.infrastructure.jpa.HubJpaRepository;
import on.logistics.hubservice.infrastructure.jpa.querydsl.HubRepositoryCustom;
import on.logistics.hubservice.presentation.dtos.response.GetSpokesLinkedToCenterResponse;
import on.logistics.hubservice.presentation.dtos.response.SearchHubResponse;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HubRepositoryImpl implements HubRepository {

    private final HubJpaRepository hubJpaRepository;
    private final HubRepositoryCustom hubRepositoryCustom;

    @Override
    public Hub save(Hub hub) {
        return hubJpaRepository.save(hub);
    }

    @Override
    public boolean existsById(UUID id) {
        return hubJpaRepository.existsById(id);
    }

    @Override
    public Optional<Hub> findByIdAndIsDeleted(UUID id, boolean isDeleted) {
        return hubJpaRepository.findByIdAndIsDeleted(id, isDeleted);
    }

    @Override
    public PageDto<SearchHubResponse> searchHub(SearchHubRequestDto requestDto) {
        return hubRepositoryCustom.searchHub(requestDto);
    }

    @Override
    public List<GetSpokesLinkedToCenterResponse> findSpokesLinkedToCenter(UUID centerId) {
        return hubRepositoryCustom.getSpokesLinkedToCenter(centerId);
    }
}
