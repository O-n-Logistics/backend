package on.logistics.productservice.application.dto;

import java.util.UUID;

public record CreateProductRequestDto(String productName, UUID companyId, UUID managedHubId,
                                      Long productQuantity, Long productPrice, Long bundleSize) {

}
