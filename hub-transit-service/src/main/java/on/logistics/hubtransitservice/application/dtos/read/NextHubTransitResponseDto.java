package on.logistics.hubtransitservice.application.dtos.read;

import java.util.UUID;

public record NextHubTransitResponseDto(
    UUID transitId,
    UUID currentHubId,
    String currentHubName,
    UUID nextHubId,
    String nextHubName,
    String nextDestinationType,
    UUID deliveryManagerId
) {

    public static NextHubTransitResponseDto of(
        UUID transitId,
        UUID currentHubId,
        String currentHubName,
        UUID nextHubId,
        String nextHubName,
        String nextDestType,
        UUID deliveryManagerId
    ) {
        return new NextHubTransitResponseDto(
            transitId, currentHubId, currentHubName, nextHubId, nextHubName, nextDestType,
            deliveryManagerId);
    }

}
