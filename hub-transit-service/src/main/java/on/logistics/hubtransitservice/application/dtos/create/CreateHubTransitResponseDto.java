package on.logistics.hubtransitservice.application.dtos.create;

import java.util.UUID;
import on.logistics.hubtransitservice.domain.entity.HubTransit;

public record CreateHubTransitResponseDto(
    UUID transitId
) {

    public static CreateHubTransitResponseDto from(HubTransit hubTransit) {
        return new CreateHubTransitResponseDto(hubTransit.getId());
    }
}
