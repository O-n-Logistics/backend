package on.logistics.hubtransitservice.application;

import java.util.UUID;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HubTransitService {

    CreateHubTransitResponseDto createHubTransit(final CreateHubTransitRequestDto requestDto);

    CreateNextHubTransitResponseDto createNextHubTransit(
        final CreateNextHubTransitRequestDto requestDto);

    GetHubTransitResponseDto getHubTransit(UUID transitId);

    Page<SearchHubTransitResponseDto> searchHubTransit(String keyword, Pageable pageable);

    NextHubTransitResponseDto getNextHubTransit(NextHubTransitRequestDto requestDto);

    UpdateHubTransitResponseDto updateHubTransit(final UpdateHubTransitRequestDto requestDto);

    void deleteHubTransit(UUID transitId);

}
