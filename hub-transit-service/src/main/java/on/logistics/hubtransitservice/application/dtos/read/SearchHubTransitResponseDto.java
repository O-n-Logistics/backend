package on.logistics.hubtransitservice.application.dtos.read;

import java.util.UUID;
import on.logistics.hubtransitservice.domain.entity.HubTransit;

public record SearchHubTransitResponseDto(
    UUID transitId,
    UUID currentHubId,
    String currentHubName,
    UUID nextHubId,
    String nextHubName,
    String nextDestinationType,
    UUID deliveryId,
    UUID deliveryManagerId
) {

    public static SearchHubTransitResponseDto from(HubTransit hubTransit) {
        return new SearchHubTransitResponseDto(
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
