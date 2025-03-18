package on.logistics.deliverymanagerservice.presentation.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateDeliveryManagerRequest(
    @NotBlank String deliveryType
) {

}
