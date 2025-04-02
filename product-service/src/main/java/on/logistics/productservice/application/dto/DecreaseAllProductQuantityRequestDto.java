package on.logistics.productservice.application.dto;

import java.util.List;
import java.util.UUID;
import on.logistics.productservice.presentation.dtos.request.DecreaseAllProductQuantityRequest;

public record DecreaseAllProductQuantityRequestDto(
    List<DecreaseProductQuantityRequestDto> products
) {

    public record DecreaseProductQuantityRequestDto(
        UUID productId,
        Long decreaseQuantity
    ) {

    }

    public static DecreaseAllProductQuantityRequestDto from(
        DecreaseAllProductQuantityRequest request
    ) {
        return new DecreaseAllProductQuantityRequestDto(
            request.products().stream()
                .map(product -> new DecreaseProductQuantityRequestDto(
                    product.productId(), product.decreaseQuantity()
                ))
                .toList()
        );
    }
}
