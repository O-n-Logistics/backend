package on.logistics.hubtransitservice.application.dtos.update;

import java.util.UUID;
import on.logistics.hubtransitservice.domain.entity.HubTransit;

public record UpdateHubTransitResponseDto(
    UUID transitId,
    UUID deliveryManagerId
) {

    public static UpdateHubTransitResponseDto from(HubTransit hubTransit) {
        return new UpdateHubTransitResponseDto(
            hubTransit.getId(),
            hubTransit.getDeliveryManagerId()
        );
    }

}
