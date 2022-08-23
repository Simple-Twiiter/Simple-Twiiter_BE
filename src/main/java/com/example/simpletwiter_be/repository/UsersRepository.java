package com.example.simpletwiter_be.repository;

import com.example.simpletwiter_be.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String email);
    boolean existsByUsername(String email);
}
