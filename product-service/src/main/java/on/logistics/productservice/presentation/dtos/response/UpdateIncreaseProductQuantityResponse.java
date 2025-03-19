package on.logistics.productservice.presentation.dtos.response;

import java.util.UUID;

public record UpdateIncreaseProductQuantityResponse(UUID productId) {

    public static UpdateIncreaseProductQuantityResponse of(UUID productId) {
        return new UpdateIncreaseProductQuantityResponse(productId);
    }

}

