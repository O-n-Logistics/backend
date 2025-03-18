package on.logistics.deliverymanagerservice.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import on.logistics.deliverymanagerservice.application.dtos.CreateDeliveryManagerRequestDto;
import on.logistics.deliverymanagerservice.application.service.DeliveryManagerService;
import on.logistics.deliverymanagerservice.global.presentation.dtos.CommonResponse;
import on.logistics.deliverymanagerservice.presentation.dtos.request.CreateDeliveryManagerRequest;
import on.logistics.deliverymanagerservice.presentation.dtos.response.CreateDeliveryManagerResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/delivery-managers")
public class DeliveryManagerController {

    private final DeliveryManagerService deliveryManagerService;

    @PostMapping
    public ResponseEntity<CommonResponse<CreateDeliveryManagerResponse>> createDeliveryManager(
        @RequestBody @Valid CreateDeliveryManagerRequest createDeliveryManagerRequest) {
        final var requestDto = CreateDeliveryManagerRequestDto.of(createDeliveryManagerRequest);
        final var responseDto = deliveryManagerService.createDeliveryManager(requestDto);
        return ResponseEntity.ok(CommonResponse.success(responseDto));
    }
}
