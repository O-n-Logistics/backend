package on.logistics.companyservice.infrastructure.jpa;

import java.util.Optional;
import java.util.UUID;
import on.logistics.companyservice.domain.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyJpaRepository extends JpaRepository<Company, UUID> {

    Optional<Company> findByUserId(UUID userId);
}
