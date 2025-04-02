package on.logistics.orderservice.infrastructure.clients.product.dtos;

import java.util.List;
import on.logistics.orderservice.domain.entity.OrderProduct;

public record DecreaseAllProductStockRequestDto(
    List<DecreaseProductStockRequestDto> allProducts
) {

    public static DecreaseAllProductStockRequestDto from(List<OrderProduct> allProducts) {
        return new DecreaseAllProductStockRequestDto(
            allProducts.stream()
                .map(DecreaseProductStockRequestDto::from)
                .toList()
        );
    }
}
