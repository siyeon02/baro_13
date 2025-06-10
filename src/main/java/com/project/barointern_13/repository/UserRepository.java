package com.project.barointern_13.repository;

import com.project.barointern_13.entity.User;

import java.util.Optional;

public interface UserRepository {
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
    Optional<User> findById(Long id);
    User save(User user);
}
