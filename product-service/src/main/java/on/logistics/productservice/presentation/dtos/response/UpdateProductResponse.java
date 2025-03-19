package on.logistics.productservice.presentation.dtos.response;

import java.util.UUID;

public record UpdateProductResponse(UUID productId) {

    public static UpdateProductResponse of(UUID productId) {
        return new UpdateProductResponse(productId);
    }

}
