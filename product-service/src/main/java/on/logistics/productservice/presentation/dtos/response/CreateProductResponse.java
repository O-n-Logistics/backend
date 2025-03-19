package on.logistics.productservice.presentation.dtos.response;

import java.util.UUID;

public record CreateProductResponse(UUID productId) {

    public static CreateProductResponse of(UUID productId) {
        return new CreateProductResponse(productId);
    }

}