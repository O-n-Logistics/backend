package on.logistics.productservice.application.dto;

import java.util.UUID;

public record UpdateProductRequestDto(UUID productId, String productName, Long productQuantity,
                                      Long productPrice,
                                      Long bundleSize) {

}
