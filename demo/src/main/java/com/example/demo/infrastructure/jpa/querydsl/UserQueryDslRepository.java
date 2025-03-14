package com.example.demo.infrastructure.jpa.querydsl;

import com.example.demo.domain.entity.User;
import java.util.Optional;


public interface UserQueryDslRepository {

    Optional<User> findByName(String name);
}
