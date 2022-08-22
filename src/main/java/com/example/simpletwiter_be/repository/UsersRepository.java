package com.example.simpletwiter_be.repository;

import com.example.simpletwiter_be.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String email);
    boolean existsByUsername(String email);
}
