package on.logistics.hubservice.presentation;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import on.logistics.hubservice.application.dtos.request.CreateHubRequestDto;
import on.logistics.hubservice.application.dtos.request.SearchHubRequestDto;
import on.logistics.hubservice.application.dtos.request.UpdateHubRequestDto;
import on.logistics.hubservice.application.service.HubService;
import on.logistics.hubservice.domain.entity.HubType;
import on.logistics.hubservice.global.application.dtos.PageDto;
import on.logistics.hubservice.global.presentation.dtos.CommonResponse;
import on.logistics.hubservice.presentation.dtos.request.CreateHubRequest;
import on.logistics.hubservice.presentation.dtos.request.UpdateHubRequest;
import on.logistics.hubservice.presentation.dtos.response.CreateHubResponse;
import on.logistics.hubservice.presentation.dtos.response.GetHubResponse;
import on.logistics.hubservice.presentation.dtos.response.GetSpokesLinkedToCenterResponse;
import on.logistics.hubservice.presentation.dtos.response.SearchHubResponse;
import on.logistics.hubservice.presentation.dtos.response.UpdateHubResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hubs")
public class HubController {

    private final HubService hubService;

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<GetHubResponse>> getHub(@PathVariable UUID id) {
        final var responseDto = hubService.getHub(id);
        return ResponseEntity.ok(CommonResponse.success(responseDto));
    }

    @GetMapping("/search")
    public ResponseEntity<CommonResponse<PageDto<SearchHubResponse>>> searchHub(
        @RequestParam(required = false) String keyword,
        @RequestParam(required = false) HubType type,
        @PageableDefault Pageable pageable) {
        final var requestDto = SearchHubRequestDto.of(keyword, type, pageable);
        final var responseDto = hubService.searchHub(requestDto);
        return ResponseEntity.ok(CommonResponse.success(responseDto));
    }

    @PostMapping
    public ResponseEntity<CommonResponse<CreateHubResponse>> createHub(
        @RequestBody @Valid CreateHubRequest createHubRequest) {
        final var requestDto = CreateHubRequestDto.of(createHubRequest);
        final var responseDto = hubService.createHub(requestDto);
        return ResponseEntity.ok(CommonResponse.success(responseDto));
    }

    @PutMapping("/{id}")
    ResponseEntity<CommonResponse<UpdateHubResponse>> updateHub(@PathVariable UUID id,
        @RequestBody @Valid UpdateHubRequest updateHubRequest) {
        final var requestDto = UpdateHubRequestDto.of(id, updateHubRequest);
        final var responseDto = hubService.updateHub(requestDto);
        return ResponseEntity.ok(CommonResponse.success(responseDto));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<CommonResponse<Void>> deleteHub(@PathVariable UUID id) {
        hubService.deleteHub(id);
        return ResponseEntity.ok(CommonResponse.success());
    }

    @GetMapping("/link")
    ResponseEntity<CommonResponse<List<GetSpokesLinkedToCenterResponse>>> getSpokesLinkedToCenter(
        @RequestParam UUID centerId) {
        final var responseDto = hubService.getSpokesLinkedToCenter(centerId);
        return ResponseEntity.ok(CommonResponse.success(responseDto));
    }
}
