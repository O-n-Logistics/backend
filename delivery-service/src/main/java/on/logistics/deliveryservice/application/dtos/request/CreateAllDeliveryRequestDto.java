package on.logistics.deliveryservice.application.dtos.request;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;
import on.logistics.deliveryservice.presentation.dtos.request.CreateAllDeliveryRequest;
import on.logistics.deliveryservice.presentation.dtos.request.CreateAllDeliveryRequest.CreateDeliveryRequest;

public record CreateAllDeliveryRequestDto(
    List<CreateDeliveryRequestDto> createDeliveryRequestDtos,
    HttpServletRequest httpServletRequest
) {

    public record CreateDeliveryRequestDto(
        UUID orderId,
        String destination,
        UUID startHubId
    ) {

        public static CreateDeliveryRequestDto from(
            CreateDeliveryRequest request
        ) {
            return new CreateDeliveryRequestDto(
                request.orderId(),
                request.destination(),
                request.startHubId()
            );
        }

    }

    public static CreateAllDeliveryRequestDto of(
        CreateAllDeliveryRequest request,
        HttpServletRequest httpServletRequest
    ) {
        return new CreateAllDeliveryRequestDto(
            request.createDeliveryRequests().stream()
                .map(CreateDeliveryRequestDto::from)
                .toList(),
            httpServletRequest
        );
    }
}
