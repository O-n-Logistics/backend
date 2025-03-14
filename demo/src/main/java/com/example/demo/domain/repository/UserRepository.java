package com.example.demo.domain.repository;

import com.example.demo.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepository {
    // 메서드
    Page<User> findAll(Pageable pageable);
}
