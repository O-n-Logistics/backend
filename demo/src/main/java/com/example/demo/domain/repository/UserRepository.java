package com.example.demo.domain.repository;

import com.example.demo.domain.entity.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepository {

    Page<User> findAll(Pageable pageable);

    Optional<User> findByName(String name);
}
