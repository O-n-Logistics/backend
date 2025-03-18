package on.logistics.deliverymanagerservice.presentation.dtos.response;

import java.util.UUID;
import lombok.Builder;

@Builder
public record CreateDeliveryManagerResponse(
    UUID deliveryManagerId
) {

    public static CreateDeliveryManagerResponse of(UUID deliveryManagerId) {
        return CreateDeliveryManagerResponse.builder()
            .deliveryManagerId(deliveryManagerId)
            .build();
    }
}
