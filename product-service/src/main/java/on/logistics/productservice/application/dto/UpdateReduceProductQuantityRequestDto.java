package on.logistics.productservice.application.dto;

import java.util.UUID;

public record UpdateReduceProductQuantityRequestDto(UUID productId, Long productQuantity) {

}
