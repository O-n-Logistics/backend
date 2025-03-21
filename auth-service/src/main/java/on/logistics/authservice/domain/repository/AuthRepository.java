package on.logistics.authservice.domain.repository;

import java.util.Optional;
import java.util.UUID;
import on.logistics.authservice.domain.entity.Auth;
import on.logistics.authservice.domain.vo.Username;

public interface AuthRepository {

    Auth save(Auth auth);

    Optional<Auth> findById(UUID uuid);

    Optional<Auth> findByUserId(UUID userId);

    Optional<Auth> findByUsername(Username username);

    boolean existsByUsername(Username username);

    void delete(Auth auth);
}
