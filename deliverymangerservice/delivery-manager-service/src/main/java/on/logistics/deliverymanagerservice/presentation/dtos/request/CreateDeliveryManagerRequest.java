package on.logistics.deliverymanagerservice.presentation.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record CreateDeliveryManagerRequest(
    @NotBlank String userId,
    @NotBlank String hubId,
    @NotBlank String deliveryType
) {

}
