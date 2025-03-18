package on.logistics.deliverymanagerservice.application.dtos;

import java.util.UUID;
import lombok.Builder;
import on.logistics.deliverymanagerservice.domain.entity.DeliveryType;
import on.logistics.deliverymanagerservice.presentation.dtos.request.UpdateDeliveryManagerRequest;

@Builder
public record UpdateDeliveryManagerRequestDto(
    UUID deliveryManagerId,
    DeliveryType deliveryType
) {

    public static UpdateDeliveryManagerRequestDto of(UUID deliveryManagerId,
        UpdateDeliveryManagerRequest request) {
        return UpdateDeliveryManagerRequestDto.builder()
            .deliveryManagerId(deliveryManagerId)
            .deliveryType(on.logistics.deliverymanagerservice.domain.entity.DeliveryType.valueOf(
                request.deliveryType()))
            .build();
    }
}
