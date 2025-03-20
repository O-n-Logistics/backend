package on.logistics.hubtransitservice.application.dtos.create;

import java.util.UUID;
import on.logistics.hubtransitservice.domain.entity.HubTransit;

public record CreateNextHubTransitResponseDto(
    UUID transitId,
    UUID currentHubId,
    String currentHubName,
    UUID nextHubId,
    String nextHubName,
    String nextDestinationType
) {

    public static CreateNextHubTransitResponseDto from(HubTransit hubTransit) {
        return new CreateNextHubTransitResponseDto(
            hubTransit.getId(),
            hubTransit.getCurrentHubId(),
            hubTransit.getCurrentHubName().getValue(),
            hubTransit.getNextHubId(),
            hubTransit.getNextHubName().getValue(),
            hubTransit.getNextDestinationType()
        );
    }

}
