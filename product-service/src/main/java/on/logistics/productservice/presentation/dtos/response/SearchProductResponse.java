package on.logistics.productservice.presentation.dtos.response;

import java.util.UUID;
import on.logistics.productservice.domain.Product;

public record SearchProductResponse(UUID productId, String productName, UUID companyId,
                                    Long productPrice) {

    public static SearchProductResponse from(Product product) {
        return new SearchProductResponse(product.getId(), product.getName().getValue(),
            product.getCompanyId(), product.getPrice().getValue());
    }
}
