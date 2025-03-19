package on.logistics.productservice.application.dto;

import java.util.UUID;

public record UpdateIncreaseProductQuantityRequestDto(UUID productId, Long productQuantity) {

}
