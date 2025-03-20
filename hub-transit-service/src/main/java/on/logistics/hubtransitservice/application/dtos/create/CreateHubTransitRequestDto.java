package on.logistics.hubtransitservice.application.dtos.create;

import java.util.UUID;

public record CreateHubTransitRequestDto(
    UUID deliveryId,
    UUID startHubId,
    String startHubName,
    UUID endHubId,
    String endHubName,
    UUID nextHubId,
    String nextHubName,
    String nextDestType
) {

    public CreateHubTransitRequestDto withInitialHubInfo(
        String startHubName,
        String endHubName,
        UUID nextHubId,
        String nextHubName,
        String nextDestType
    ) {
        return new CreateHubTransitRequestDto(
            this.deliveryId,
            this.startHubId,
            startHubName,
            this.endHubId,
            endHubName,
            nextHubId,
            nextHubName,
            nextDestType
        );
    }

}
