package com.project.barointern_13.repository;

import com.project.barointern_13.entity.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private final Map<Long, User> store = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public boolean existsByUsername(String username) {
        return store.values().stream()
                .anyMatch(user -> user.getUsername().equals(username));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return store.values().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            user.setId(idGenerator.getAndIncrement());
        }
        store.put(user.getId(), user);
        return user;
    }
}


