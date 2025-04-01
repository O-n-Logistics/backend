package on.logistics.productservice.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import on.logistics.productservice.application.dto.SearchProductRequestDto;
import on.logistics.productservice.domain.Product;
import org.springframework.data.domain.Page;

public interface ProductRepository {

    Product save(Product product);

    Page<Product> searchProduct(SearchProductRequestDto requestDto);

    Optional<Product> findAllById(UUID uuid);

    List<Product> findAllById(List<UUID> productIds);

    void delete(Product product);
}
