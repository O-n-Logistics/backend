package on.logistics.authservice.infrastructure.jpa;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import on.logistics.authservice.domain.entity.Auth;
import on.logistics.authservice.domain.repository.AuthRepository;
import on.logistics.authservice.domain.vo.Username;
import on.logistics.authservice.infrastructure.querydsl.AuthQueryDslRepository;
import on.logistics.authservice.infrastructure.querydsl.AuthQueryDslRepositoryImpl;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AuthRepositoryImpl implements AuthRepository, AuthQueryDslRepository {

    private final AuthJpaRepository authJpaRepository;
    private final AuthQueryDslRepositoryImpl authQueryDslRepository;

    @Override
    public Auth save(Auth auth) {
        return authJpaRepository.save(auth);
    }

    @Override
    public Optional<Auth> findById(UUID uuid) {
        return authQueryDslRepository.findById(uuid);
    }

    @Override
    public Optional<Auth> findByUserId(UUID userId) {
        return authJpaRepository.findByUserId(userId);
    }

    @Override
    public Optional<Auth> findByUsername(Username username) {
        return authJpaRepository.findByUsername(username);
    }

    @Override
    public boolean existsByUsername(Username username) {
        return authJpaRepository.existsByUsername(username);
    }

    @Override
    public void delete(Auth auth) {
        authJpaRepository.delete(auth);
    }
}
