package on.logistics.deliveryservice.domain.dtos;

import java.util.UUID;
import on.logistics.deliveryservice.application.dtos.DeliveryHubInfoDto;
import on.logistics.deliveryservice.application.dtos.DeliveryUserInfoDto;
import on.logistics.deliveryservice.application.dtos.request.CreateDeliveryEntityRequestDto;

public record CreateDeliveryDto(UUID orderId, UUID startHubId, UUID endHubId, String destination,
                                String recipient, String recipientSlackEmail) {

    public static CreateDeliveryDto from(CreateDeliveryEntityRequestDto request,
        DeliveryHubInfoDto hubInfo, DeliveryUserInfoDto userInfo) {
        return new CreateDeliveryDto(request.orderId(), request.startHubId(), hubInfo.endHubId(),
            request.destination(), userInfo.recipient(), userInfo.recipientSlackEmail());
    }

}
