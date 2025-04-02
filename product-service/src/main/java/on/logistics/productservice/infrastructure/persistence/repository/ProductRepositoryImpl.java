package on.logistics.productservice.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import on.logistics.productservice.application.dto.SearchProductRequestDto;
import on.logistics.productservice.domain.Product;
import on.logistics.productservice.domain.repository.ProductRepository;
import on.logistics.productservice.infrastructure.persistence.jpa.ProductJpaRepository;
import on.logistics.productservice.infrastructure.persistence.querydsl.ProductQueryRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;
    private final ProductQueryRepository productQueryRepository;

    @Override
    public Product save(Product product) {
        return productJpaRepository.save(product);
    }

    @Override
    public Page<Product> searchProduct(SearchProductRequestDto requestDto) {
        return productQueryRepository.searchProduct(requestDto);
    }

    @Override
    public Optional<Product> findAllById(UUID id) {
        return productJpaRepository.findById(id);
    }

    @Override
    public List<Product> findAllById(List<UUID> productIds) {
        return productJpaRepository.findAllById(productIds);
    }

    @Override
    public void delete(Product product) {
        productJpaRepository.delete(product);
    }
}
