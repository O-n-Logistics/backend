package on.logistics.deliveryservice.presentation.dtos.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public record CreateAllDeliveryRequest(
    @NotEmpty List<CreateDeliveryRequest> createDeliveryRequests
) {

    public record CreateDeliveryRequest(
        @NotNull(message = "주문 ID는 필수 값입니다.") UUID orderId,
        @NotNull(message = "목적지는 필수 입력 값입니다.") String destination,
        @NotNull(message = "스타트 허브는 필수 입력 값입니다.") UUID startHubId
    ) {

    }

}
