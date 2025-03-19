package on.logistics.productservice.infrastructure.querydsl;

import on.logistics.productservice.application.dto.SearchProductRequestDto;
import on.logistics.productservice.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductQueryRepository {

    Page<Product> searchProduct(SearchProductRequestDto requestDto);
}
