package on.logistics.hubtransitservice.presentation;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import on.logistics.hubtransitservice.application.HubTransitService;
import on.logistics.hubtransitservice.application.dtos.create.CreateHubTransitResponseDto;
import on.logistics.hubtransitservice.application.dtos.create.CreateNextHubTransitResponseDto;
import on.logistics.hubtransitservice.application.dtos.read.GetHubTransitResponseDto;
import on.logistics.hubtransitservice.application.dtos.read.NextHubTransitRequestDto;
import on.logistics.hubtransitservice.application.dtos.read.NextHubTransitResponseDto;
import on.logistics.hubtransitservice.application.dtos.read.SearchHubTransitResponseDto;
import on.logistics.hubtransitservice.application.dtos.update.UpdateHubTransitRequestDto;
import on.logistics.hubtransitservice.application.dtos.update.UpdateHubTransitResponseDto;
import on.logistics.hubtransitservice.global.presentation.dtos.CommonResponse;
import on.logistics.hubtransitservice.global.presentation.dtos.PageDto;
import on.logistics.hubtransitservice.presentation.dtos.create.CreateHubTransitRequest;
import on.logistics.hubtransitservice.presentation.dtos.create.CreateNextHubTransitRequest;
import on.logistics.hubtransitservice.presentation.dtos.update.UpdateHubTransitRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hub-transit")
public class HubTransitController {

    private final HubTransitService hubTransitService;

    @PostMapping("/route")
    public ResponseEntity<CommonResponse<CreateHubTransitResponseDto>> createHubTransit(
        @RequestBody @Valid final CreateHubTransitRequest request
    ) {
        final var requestDto = CreateHubTransitRequest.from(request);
        final var responseDto = hubTransitService.createHubTransit(requestDto);
        return ResponseEntity.ok(CommonResponse.success(responseDto));
    }

    @PostMapping("/route/next")
    public ResponseEntity<CommonResponse<CreateNextHubTransitResponseDto>> createNextHubTransit(
        @RequestBody @Valid final CreateNextHubTransitRequest request
    ) {
        final var requestDto = CreateNextHubTransitRequest.from(request);
        final var responseDto = hubTransitService.createNextHubTransit(requestDto);
        return ResponseEntity.ok(CommonResponse.success(responseDto));
    }

    @GetMapping("/{transitId}")
    public ResponseEntity<CommonResponse<GetHubTransitResponseDto>> getHubTransit(
        @PathVariable UUID transitId
    ) {
        final var responseDto = hubTransitService.getHubTransit(transitId);
        return ResponseEntity.ok(CommonResponse.success(responseDto));
    }

    @GetMapping("/search")
    public ResponseEntity<CommonResponse<PageDto<SearchHubTransitResponseDto>>> searchHubTransit(
        @RequestParam(required = false) String keyword, @PageableDefault Pageable pageable
    ) {
        final var resultPage = hubTransitService.searchHubTransit(keyword, pageable);
        final var pageDto = PageDto.from(resultPage);
        return ResponseEntity.ok(CommonResponse.success(pageDto));
    }

    @GetMapping("/next")
    public ResponseEntity<CommonResponse<NextHubTransitResponseDto>> getNextHubTransit(
        @RequestParam("transitId") UUID transitId, @RequestParam("currentHubId") UUID currentHubId
    ) {
        final var requestDto = NextHubTransitRequestDto.of(transitId, currentHubId);
        final var responseDto = hubTransitService.getNextHubTransit(requestDto);
        return ResponseEntity.ok(CommonResponse.success(responseDto));
    }

    @PatchMapping("/{transitId}")
    public ResponseEntity<CommonResponse<UpdateHubTransitResponseDto>> updateHubTransit(
        @PathVariable UUID transitId,
        @RequestBody @Valid UpdateHubTransitRequest request
    ) {
        final var requestDto = UpdateHubTransitRequestDto.of(transitId, request);
        final var responseDto = hubTransitService.updateHubTransit(requestDto);
        return ResponseEntity.ok(CommonResponse.success(responseDto));
    }

    @DeleteMapping("/{transitId}")
    public ResponseEntity<CommonResponse<Void>> deleteHubTransit(
        @PathVariable UUID transitId
    ) {
        hubTransitService.deleteHubTransit(transitId);
        return ResponseEntity.ok(CommonResponse.success());
    }

}
