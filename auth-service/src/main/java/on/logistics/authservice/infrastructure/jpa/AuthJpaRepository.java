package on.logistics.authservice.infrastructure.jpa;

import java.util.Optional;
import java.util.UUID;
import on.logistics.authservice.domain.entity.Auth;
import on.logistics.authservice.domain.vo.Username;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthJpaRepository extends JpaRepository<Auth, UUID> {

    Optional<Auth> findByUsername(Username username);

    Optional<Auth> findByUserId(UUID userId);

    boolean existsByUsername(Username username);
}
