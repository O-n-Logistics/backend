package on.logistics.orderservice.application.service;

import on.logistics.orderservice.infrastructure.clients.product.dtos.DecreaseAllProductStockRequestDto;
import on.logistics.orderservice.infrastructure.clients.product.dtos.DecreaseProductStockRequestDto;
import on.logistics.orderservice.infrastructure.clients.product.dtos.RollbackDecreaseProductStockRequestDto;

public interface ProductService {

    void decreaseProductStock(DecreaseProductStockRequestDto requestDto);

    void rollbackDecreaseProductStock(RollbackDecreaseProductStockRequestDto requestDto);

    void decreaseAllProductStock(DecreaseAllProductStockRequestDto allProducts);
}
