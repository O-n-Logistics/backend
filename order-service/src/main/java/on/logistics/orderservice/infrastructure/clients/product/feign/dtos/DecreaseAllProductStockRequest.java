package on.logistics.orderservice.infrastructure.clients.product.feign.dtos;

import java.util.List;
import java.util.UUID;
import on.logistics.orderservice.infrastructure.clients.product.dtos.DecreaseAllProductStockRequestDto;

public record DecreaseAllProductStockRequest(
    List<DecreaseProductStockRequest> products
) {

    public record DecreaseProductStockRequest(
        UUID productId,
        Long decreaseQuantity
    ) {

        public static DecreaseProductStockRequest of(
            UUID productId,
            Long decreaseQuantity
        ) {
            return new DecreaseProductStockRequest(
                productId,
                decreaseQuantity
            );
        }
    }

    public static DecreaseAllProductStockRequest from(
        DecreaseAllProductStockRequestDto requestDto
    ) {
        return new DecreaseAllProductStockRequest(
            requestDto.allProducts().stream()
                .map(product -> DecreaseProductStockRequest.of(
                    product.productId(),
                    product.quantity()
                ))
                .toList()
        );
    }
}
