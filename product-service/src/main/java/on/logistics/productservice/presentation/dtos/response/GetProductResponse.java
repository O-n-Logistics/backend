package on.logistics.productservice.presentation.dtos.response;

import java.util.UUID;
import on.logistics.productservice.domain.Product;

public record GetProductResponse(UUID productId, String productName, UUID companyId,
                                 UUID managedHubId, Long productQuantity, Long productPrice,
                                 Long bundleSize) {

    public static GetProductResponse from(Product product) {
        return new GetProductResponse(product.getId(), product.getName().getValue(),
            product.getCompanyId(), product.getManagedHubId(), product.getQuantity().getValue(),
            product.getPrice().getValue(), product.getBundleSize().getValue());
    }
}
