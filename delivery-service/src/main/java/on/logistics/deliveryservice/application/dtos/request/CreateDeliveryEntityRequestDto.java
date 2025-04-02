package on.logistics.deliveryservice.application.dtos.request;

import java.util.UUID;
import on.logistics.deliveryservice.application.service.DeliveryServiceImpl;

public record CreateDeliveryEntityRequestDto(
    UUID orderId,
    String destination,
    UUID startHubId
) {

    public static CreateDeliveryEntityRequestDto from(
        CreateDeliveryRequestDto request
    ) {
        return new CreateDeliveryEntityRequestDto(
            request.orderId(),
            request.destination(),
            request.startHubId()
        );
    }

    public static CreateDeliveryEntityRequestDto from(
        CreateAllDeliveryRequestDto.CreateDeliveryRequestDto request
    ) {
        return new CreateDeliveryEntityRequestDto(
            request.orderId(),
            request.destination(),
            request.startHubId()
        );
    }
}
