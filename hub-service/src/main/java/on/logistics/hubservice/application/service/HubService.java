package on.logistics.hubservice.application.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import on.logistics.hubservice.application.clients.map.MapServiceClient;
import on.logistics.hubservice.application.clients.map.feign.dtos.GetGeocodeResponse;
import on.logistics.hubservice.application.dtos.request.CreateHubRequestDto;
import on.logistics.hubservice.application.dtos.request.SearchHubRequestDto;
import on.logistics.hubservice.application.dtos.request.UpdateHubRequestDto;
import on.logistics.hubservice.domain.entity.Hub;
import on.logistics.hubservice.domain.repository.HubRepository;
import on.logistics.hubservice.exception.HubException;
import on.logistics.hubservice.exception.HubExceptionCode;
import on.logistics.hubservice.global.application.dtos.PageDto;
import on.logistics.hubservice.presentation.dtos.response.CreateHubResponse;
import on.logistics.hubservice.presentation.dtos.response.GetHubResponse;
import on.logistics.hubservice.presentation.dtos.response.GetSpokesLinkedToCenterResponse;
import on.logistics.hubservice.presentation.dtos.response.SearchHubResponse;
import on.logistics.hubservice.presentation.dtos.response.UpdateHubResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HubService {

    private final HubRepository hubRepository;
    private final MapServiceClient mapServiceClient;

    @Transactional(readOnly = true)
    public GetHubResponse getHub(final UUID id) {
        Hub hub = findHubById(id);
        return GetHubResponse.of(hub);
    }

    @Transactional(readOnly = true)
    public PageDto<SearchHubResponse> searchHub(SearchHubRequestDto requestDto) {
        PageDto<SearchHubResponse> responsePageDto = hubRepository.searchHub(requestDto);
        return responsePageDto;
    }

    @Transactional
    public CreateHubResponse createHub(CreateHubRequestDto requestDto) {
        GetGeocodeResponse geocodeResponse = mapServiceClient.getGeocode(requestDto.hubAddress());
        BigDecimal latitude = new BigDecimal(geocodeResponse.latitude());
        BigDecimal longitude = new BigDecimal(geocodeResponse.longitude());
        Hub hub = Hub.create(requestDto, latitude, longitude);
        Hub savedHub = hubRepository.save(hub);
        return CreateHubResponse.of(savedHub.getId());
    }

    @Transactional
    public UpdateHubResponse updateHub(UpdateHubRequestDto requestDto) {
        Hub hub = findHubById(requestDto.id());
        hub.update(requestDto);
        return UpdateHubResponse.of(hub);
    }

    @Transactional
    public void deleteHub(final UUID id) {
        Hub hub = findHubById(id);
        hub.delete();
    }

    @Transactional(readOnly = true)
    public List<GetSpokesLinkedToCenterResponse> getSpokesLinkedToCenter(final UUID centerId) {
        Hub centerHub = findHubById(centerId);
        final var response = hubRepository.findSpokesLinkedToCenter(centerHub.getId());
        return response;
    }

    private Hub findHubById(UUID id) {
        return hubRepository.findByIdAndIsDeleted(id, false)
            .orElseThrow(() -> new HubException(HubExceptionCode.HUB_NOT_FOUND));
    }
}
