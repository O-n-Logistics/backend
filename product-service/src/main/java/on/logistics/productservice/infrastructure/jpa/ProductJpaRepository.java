package on.logistics.productservice.infrastructure.jpa;

import java.util.UUID;
import on.logistics.productservice.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJpaRepository extends JpaRepository<Product, UUID> {

}
