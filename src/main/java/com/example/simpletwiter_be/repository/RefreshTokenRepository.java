package com.example.simpletwiter_be.repository;

import com.example.simpletwiter_be.domain.Member;
import com.example.simpletwiter_be.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByMember(Member member);
}
