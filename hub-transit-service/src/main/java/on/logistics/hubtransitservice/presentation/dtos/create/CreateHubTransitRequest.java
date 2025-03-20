package on.logistics.hubtransitservice.presentation.dtos.create;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import on.logistics.hubtransitservice.application.dtos.create.CreateHubTransitRequestDto;

public record CreateHubTransitRequest(
    @NotNull(message = "배송 ID는 필수입니다.") UUID deliveryId,
    @NotNull(message = "출발 허브 ID는 필수입니다.") UUID startHubId,
    @NotNull(message = "도착 허브 ID는 필수입니다.") UUID endHubId
) {

    public static CreateHubTransitRequestDto from(CreateHubTransitRequest request) {
        return new CreateHubTransitRequestDto(
            request.deliveryId,
            request.startHubId,
            null,
            request.endHubId,
            null,
            null,
            null,
            null
        );
    }

}
