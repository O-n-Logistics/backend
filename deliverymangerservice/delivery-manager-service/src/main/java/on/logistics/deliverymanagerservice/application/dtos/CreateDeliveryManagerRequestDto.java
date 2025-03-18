package on.logistics.deliverymanagerservice.application.dtos;

import java.util.UUID;
import lombok.Builder;
import on.logistics.deliverymanagerservice.domain.entity.DeliveryType;
import on.logistics.deliverymanagerservice.presentation.dtos.request.CreateDeliveryManagerRequest;

@Builder
public record CreateDeliveryManagerRequestDto(
    UUID userId,
    UUID hubId,
    DeliveryType deliveryType
) {

    public static CreateDeliveryManagerRequestDto of(CreateDeliveryManagerRequest request) {
        return CreateDeliveryManagerRequestDto.builder()
            .userId(UUID.fromString(request.userId()))
            .hubId(UUID.fromString(request.hubId()))
            .deliveryType(DeliveryType.valueOf(request.deliveryType()))
            .build();
    }
}
