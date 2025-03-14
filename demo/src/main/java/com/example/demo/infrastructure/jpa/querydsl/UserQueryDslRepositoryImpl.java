package com.example.demo.infrastructure.jpa.querydsl;

import com.example.demo.domain.entity.User;
import java.util.Optional;

public class UserQueryDslRepositoryImpl implements UserQueryDslRepository {

    @Override
    public Optional<User> findByName(String name) {
        return Optional.empty();
    }
}
