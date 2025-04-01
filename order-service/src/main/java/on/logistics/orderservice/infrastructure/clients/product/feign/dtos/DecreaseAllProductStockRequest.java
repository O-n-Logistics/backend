package on.logistics.orderservice.infrastructure.clients.product.feign.dtos;

import java.util.List;
import on.logistics.orderservice.infrastructure.clients.product.dtos.DecreaseAllProductStockRequestDto;

public record DecreaseAllProductStockRequest(
    List<DecreaseProductStockRequest> allProducts
) {

    public static DecreaseAllProductStockRequest from(
        DecreaseAllProductStockRequestDto requestDto) {
        return new DecreaseAllProductStockRequest(
            requestDto.allProducts().stream()
                .map(DecreaseProductStockRequest::from)
                .toList()
        );
    }
}
