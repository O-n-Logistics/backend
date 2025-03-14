package com.example.demo.infrastructure.repository;

import com.example.demo.domain.entity.User;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.infrastructure.jpa.UserJpaRepository;
import com.example.demo.infrastructure.jpa.querydsl.UserQueryDslRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository, UserQueryDslRepository {

    private final UserJpaRepository jpaRepository;

    @Override
    public Page<User> findAll(Pageable pageable) {
        return jpaRepository.findAll(pageable);
    }

    @Override
    public Optional<User> findByName(String name) {
        return jpaRepository.findByName(name);
    }
}
