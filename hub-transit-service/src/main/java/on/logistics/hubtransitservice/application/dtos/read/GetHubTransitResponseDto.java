package on.logistics.hubtransitservice.application.dtos.read;

import java.util.UUID;
import on.logistics.hubtransitservice.domain.entity.HubTransit;

public record GetHubTransitResponseDto(
    UUID transitId,
    UUID currentHubId,
    String currentHubName,
    UUID nextHubId,
    String nextHubName,
    String nextDestinationType,
    UUID deliveryId,
    UUID deliveryManagerId
) {

    public static GetHubTransitResponseDto from(HubTransit hubTransit) {
        return new GetHubTransitResponseDto(
            hubTransit.getId(),
            hubTransit.getCurrentHubId(),
            hubTransit.getCurrentHubName().getValue(),
            hubTransit.getNextHubId(),
            hubTransit.getNextHubName().getValue(),
            hubTransit.getNextDestinationType(),
            hubTransit.getDeliveryId(),
            hubTransit.getDeliveryManagerId()
        );
    }

}
