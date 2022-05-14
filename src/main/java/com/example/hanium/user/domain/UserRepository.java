package com.example.hanium.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
    Boolean existsByEmail(String email);
}
