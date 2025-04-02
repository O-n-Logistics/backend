package on.logistics.orderservice.infrastructure.clients.product;

import feign.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import on.logistics.orderservice.application.service.ProductService;
import on.logistics.orderservice.global.utils.FeignClientResponseUtils;
import on.logistics.orderservice.infrastructure.clients.product.dtos.DecreaseAllProductStockRequestDto;
import on.logistics.orderservice.infrastructure.clients.product.dtos.DecreaseProductStockRequestDto;
import on.logistics.orderservice.infrastructure.clients.product.dtos.RollbackDecreaseProductStockRequestDto;
import on.logistics.orderservice.infrastructure.clients.product.feign.ProductServiceFeignClient;
import on.logistics.orderservice.infrastructure.clients.product.feign.dtos.DecreaseAllProductStockRequest;
import on.logistics.orderservice.infrastructure.clients.product.feign.dtos.DecreaseProductStockRequest;
import on.logistics.orderservice.infrastructure.clients.product.feign.dtos.IncreaseProductStockRequest;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductServiceFeignClient productServiceFeignClient;

    @Override
    public void decreaseProductStock(DecreaseProductStockRequestDto requestDto) {
        log.info("Decreasing product stock: {}", requestDto);
        var decreaseProductStockRequest = DecreaseProductStockRequest.from(requestDto);
        Response response = productServiceFeignClient.decreaseProductStock(
            requestDto.productId(),
            decreaseProductStockRequest
        );
        FeignClientResponseUtils.validateResponseStatus(response);
    }

    @Override
    public void rollbackDecreaseProductStock(RollbackDecreaseProductStockRequestDto requestDto) {
        log.info("Rollback decrease product stock: {}", requestDto);
        var increaseProductStockRequest = IncreaseProductStockRequest.from(requestDto);
        Response response = productServiceFeignClient.increaseProductStock(
            requestDto.productId(),
            increaseProductStockRequest
        );
        FeignClientResponseUtils.validateResponseStatus(response);
    }

    @Override
    public void decreaseAllProductStock(DecreaseAllProductStockRequestDto requestDto) {
        log.info("Decreasing all product stock: {}", requestDto);
        var request = DecreaseAllProductStockRequest.from(requestDto);
        Response response = productServiceFeignClient.decreaseAllProductStock(request);
        FeignClientResponseUtils.validateResponseStatus(response);
    }
}
