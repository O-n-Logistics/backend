package on.logistics.deliveryservice.presentation.dtos;

import java.util.List;
import java.util.UUID;
import on.logistics.deliveryservice.domain.entity.Delivery;

public record CreateAllDeliveryResponse(
    List<UUID> deliveryIds
) {

    public static CreateAllDeliveryResponse from(List<Delivery> deliveries) {
        return new CreateAllDeliveryResponse(deliveries.stream()
            .map(Delivery::getId)
            .toList());
    }
}
