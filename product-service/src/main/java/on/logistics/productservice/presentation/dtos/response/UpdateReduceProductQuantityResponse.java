package on.logistics.productservice.presentation.dtos.response;

import java.util.UUID;

public record UpdateReduceProductQuantityResponse(UUID productId) {

    public static UpdateReduceProductQuantityResponse of(UUID productId) {
        return new UpdateReduceProductQuantityResponse(productId);
    }

}
