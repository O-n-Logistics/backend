package on.logistics.hubtransitservice.domain.dtos;

import java.util.UUID;
import on.logistics.hubtransitservice.application.dtos.create.CreateHubTransitRequestDto;

public record CreateHubTransitDto(
    UUID deliveryId,
    UUID startHubId,
    String startHubName,
    UUID endHubId,
    String endHubName
) {

    public static CreateHubTransitDto of(CreateHubTransitRequestDto dto) {
        return new CreateHubTransitDto(
            dto.deliveryId(),
            dto.startHubId(),
            dto.startHubName(),
            dto.endHubId(),
            dto.endHubName()
        );
    }

}
