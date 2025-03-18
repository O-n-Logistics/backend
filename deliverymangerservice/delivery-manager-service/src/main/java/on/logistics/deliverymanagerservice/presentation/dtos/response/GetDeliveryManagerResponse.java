package on.logistics.deliverymanagerservice.presentation.dtos.response;

import java.util.UUID;
import lombok.Builder;
import on.logistics.deliverymanagerservice.domain.entity.DeliveryManager;

@Builder
public record GetDeliveryManagerResponse(
    UUID id,
    String name,
    String hubId,
    String slackId,
    String deliveryType,
    Integer sequence
) {

    public static GetDeliveryManagerResponse of(DeliveryManager deliveryManager, String name,
        String slackId) {
        return GetDeliveryManagerResponse.builder()
            .id(deliveryManager.getId())
            .name(name)
            .hubId(String.valueOf(deliveryManager.getHubId()))
            .slackId(slackId)
            .deliveryType(String.valueOf(deliveryManager.getType()))
            .sequence(deliveryManager.getSequence())
            .build();
    }
}
