package on.logistics.hubtransitservice.application;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import on.logistics.hubtransitservice.application.dtos.create.CreateHubTransitRequestDto;
import on.logistics.hubtransitservice.application.dtos.create.CreateHubTransitResponseDto;
import on.logistics.hubtransitservice.application.dtos.create.CreateNextHubTransitRequestDto;
import on.logistics.hubtransitservice.application.dtos.create.CreateNextHubTransitResponseDto;
import on.logistics.hubtransitservice.application.dtos.read.GetHubTransitResponseDto;
import on.logistics.hubtransitservice.application.dtos.read.NextHubTransitRequestDto;
import on.logistics.hubtransitservice.application.dtos.read.NextHubTransitResponseDto;
import on.logistics.hubtransitservice.application.dtos.read.SearchHubTransitResponseDto;
import on.logistics.hubtransitservice.application.dtos.update.UpdateHubTransitRequestDto;
import on.logistics.hubtransitservice.application.dtos.update.UpdateHubTransitResponseDto;
import on.logistics.hubtransitservice.domain.dtos.CreateHubTransitDto;
import on.logistics.hubtransitservice.domain.entity.HubTransit;
import on.logistics.hubtransitservice.domain.entity.Route;
import on.logistics.hubtransitservice.domain.repository.HubTransitRepository;
import on.logistics.hubtransitservice.domain.repository.RouteRepository;
import on.logistics.hubtransitservice.exception.HubTransitException;
import on.logistics.hubtransitservice.exception.HubTransitExceptionCode;
import on.logistics.hubtransitservice.infrastructure.clients.hub.HubServiceClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HubTransitServiceImpl implements HubTransitService {

    private final HubTransitRepository hubTransitRepository;
    private final RouteRepository routeRepository;
    private final HubServiceClient hubServiceClient;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public CreateHubTransitResponseDto createHubTransit(
        final CreateHubTransitRequestDto requestDto
    ) {
        log.info("허브 이동정보 생성 요청");
        var startHubResponse = getHubInfo(requestDto.startHubId());
        var endHubResponse = getHubInfo(requestDto.endHubId());

        var route = getRouteByHubNames(startHubResponse.name, endHubResponse.name);
        String nextHubName = determineNextHubName(route.getPathJson(), startHubResponse.name);
        UUID nextHubId = getNextHubIdByName(nextHubName);
        String nextDestinationType = getNextDestinationType(nextHubName);

        CreateHubTransitRequestDto updatedRequestDto = requestDto.withInitialHubInfo(
            startHubResponse.name,
            endHubResponse.name,
            nextHubId,
            nextHubName,
            nextDestinationType
        );
        CreateHubTransitDto createDto = CreateHubTransitDto.of(updatedRequestDto);
        HubTransit hubTransit = HubTransit.create(createDto);
        HubTransit saved = hubTransitRepository.save(hubTransit);
        log.info("허브 이동정보 생성 완료, id: {}", saved.getId());
        return CreateHubTransitResponseDto.from(saved);
    }

    @Override
    @Transactional
    public CreateNextHubTransitResponseDto createNextHubTransit(
        final CreateNextHubTransitRequestDto requestDto
    ) {
        log.info("후속 허브 이동 요청, transitId: {}, currentHubId: {}", requestDto.transitId(),
            requestDto.currentHubId());

        HubTransit currentHubTransit = getOrElseThrow(requestDto.transitId());
        String newCurrentHubName = currentHubTransit.getNextHubName().getValue();

        Route route = getRouteByHubNames(
            currentHubTransit.getInitialStartHubName().getValue(),
            currentHubTransit.getInitialEndHubName().getValue()
        );

        String newNextHubName = determineNextHubName(route.getPathJson(), newCurrentHubName);
        UUID newNextHubId = getNextHubIdByName(newNextHubName);
        String nextDestinationType = getNextDestinationType(newNextHubName);

        HubTransit nextTransit = HubTransit.createNext(
            currentHubTransit,
            newNextHubId,
            newNextHubName,
            nextDestinationType
        );
        HubTransit saved = hubTransitRepository.save(nextTransit);
        log.info("후속 허브 이동정보 생성 완료, new transitId: {}", saved.getId());
        return CreateNextHubTransitResponseDto.from(saved);
    }

    @Override
    public GetHubTransitResponseDto getHubTransit(UUID transitId) {
        HubTransit hubTransit = getOrElseThrow(transitId);
        log.info("허브 이동정보 조회 성공, transitId: {}", transitId);
        return GetHubTransitResponseDto.from(hubTransit);
    }

    @Override
    public Page<SearchHubTransitResponseDto> searchHubTransit(String keyword, Pageable pageable) {
        log.info("Service - 검색 조건: keyword={}", keyword);
        return hubTransitRepository.searchHubTransit(keyword, pageable)
            .map(SearchHubTransitResponseDto::from);
    }

    @Override
    @Transactional
    public NextHubTransitResponseDto getNextHubTransit(NextHubTransitRequestDto requestDto) {
        log.info("다음 허브 이동정보 조회 요청, transitId: {}", requestDto.transitId());

        HubTransit hubTransit = getOrElseThrow(requestDto.transitId());
        String currentHubName = hubTransit.getCurrentHubName().getValue();
        Route route = getRouteByHubNames(hubTransit.getInitialStartHubName().getValue(),
            hubTransit.getInitialEndHubName().getValue()
        );

        String nextHubName = determineNextHubName(route.getPathJson(), currentHubName);
        UUID nextHubId = getNextHubIdByName(nextHubName);
        String nextDestinationType = getNextDestinationType(nextHubName);

        return NextHubTransitResponseDto.of(
            hubTransit.getId(),
            hubTransit.getCurrentHubId(),
            hubTransit.getCurrentHubName().getValue(),
            nextHubId,
            nextHubName,
            nextDestinationType,
            hubTransit.getDeliveryManagerId()
        );
    }

    @Override
    @Transactional
    public UpdateHubTransitResponseDto updateHubTransit(
        final UpdateHubTransitRequestDto requestDto) {
        log.info("배송 담당자 업데이트 요청, dto: {}", requestDto);
        HubTransit hubTransit = getOrElseThrow(requestDto.transitId());
        hubTransit.updateDeliveryManagerId(requestDto.deliveryManagerId());
        log.info("배송 담당자 업데이트 완료, transitId: {}", hubTransit.getId());
        return UpdateHubTransitResponseDto.from(hubTransit);
    }

    @Override
    @Transactional
    public void deleteHubTransit(UUID transitId) {
        log.info("삭제 요청 받은 transitId: {}", transitId);
        HubTransit hubTransit = getOrElseThrow(transitId);
        hubTransit.deleteSoftly();
        hubTransitRepository.save(hubTransit);
        log.info("허브 이동정보 soft delted, transitId: {}", transitId);
    }

    private HubTransit getOrElseThrow(UUID routeId) {
        return hubTransitRepository.findById(routeId)
            .orElseThrow(
                () -> new HubTransitException(HubTransitExceptionCode.HUB_TRANSIT_NOT_FOUND));
    }

    private HubInfo getHubInfo(UUID hubId) {
        var hubResponse = hubServiceClient.getHubById(hubId);
        if (hubResponse == null) {
            throw new HubTransitException(HubTransitExceptionCode.HUB_TRANSIT_NOT_FOUND);
        }
        return new HubInfo(hubResponse.id(), hubResponse.hubName(), hubResponse.hubType());
    }

    private Route getRouteByHubNames(String startHubName, String endHubName) {
        return routeRepository.findByStartHubNameAndEndHubName(startHubName, endHubName)
            .orElseThrow(() -> new HubTransitException(HubTransitExceptionCode.ROUTE_NOT_FOUND));
    }

    private String determineNextHubName(String pathJson, String currentHubName) {
        try {
            List<String> routePath = objectMapper.readValue(pathJson, new TypeReference<>() {
            });
            int currentIndex = routePath.indexOf(currentHubName);
            if (currentIndex == -1) {
                throw new HubTransitException(HubTransitExceptionCode.ROUTE_INVALID);
            }
            if (currentIndex < routePath.size() - 1) {
                return routePath.get(currentIndex + 1);
            } else {
                return "END_OF_HUB";
            }
        } catch (Exception e) {
            throw new HubTransitException(HubTransitExceptionCode.ROUTE_JSON_PARSE_ERROR);
        }
    }

    private UUID getNextHubIdByName(String nextHubName) {
        if ("END_OF_HUB".equals(nextHubName)) {
            return UUID.fromString("00000000-0000-0000-0000-000000000000");
        }
        var hubResponse = hubServiceClient.getHubByName(nextHubName);
        return hubResponse != null ?
            hubResponse.id() : UUID.fromString("00000000-0000-0000-0000-000000000000");
    }

    private String getNextDestinationType(String nextHubName) {
        return "END_OF_HUB".equals(nextHubName) ? "COMPANY" : "HUB";
    }

    private record HubInfo(UUID id, String name, String hubType) {

    }

}
