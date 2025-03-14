package com.example.demo.infrastructure.jpa;

import com.example.demo.domain.entity.User;
import com.example.demo.infrastructure.jpa.querydsl.UserQueryDslRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long>, UserQueryDslRepository {

}
