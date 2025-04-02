package on.logistics.orderservice.infrastructure.clients.product.feign;

import feign.Response;
import java.util.UUID;
import on.logistics.orderservice.infrastructure.clients.product.feign.dtos.DecreaseAllProductStockRequest;
import on.logistics.orderservice.infrastructure.clients.product.feign.dtos.DecreaseProductStockRequest;
import on.logistics.orderservice.infrastructure.clients.product.feign.dtos.IncreaseProductStockRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "product-service")
public interface ProductServiceFeignClient {

    @PutMapping("/api/v1/product/reduce/quantity/{productId}")
    Response decreaseProductStock(
        @PathVariable UUID productId,
        @RequestBody DecreaseProductStockRequest request
    );

    @PutMapping("/api/v1/product/increase/quantity/{productId}")
    Response increaseProductStock(
        @PathVariable UUID productId,
        @RequestBody IncreaseProductStockRequest request
    );

    @PutMapping("/api/v1/product/reduce/quantity/all")
    Response decreaseAllProductStock(@RequestBody DecreaseAllProductStockRequest request);
}
