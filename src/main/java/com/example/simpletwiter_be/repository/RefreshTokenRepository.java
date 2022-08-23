package com.example.simpletwiter_be.repository;

import com.example.simpletwiter_be.domain.Member;
import com.example.simpletwiter_be.domain.RefreshToken;
<<<<<<< HEAD
=======
import com.example.simpletwiter_be.domain.Member;
>>>>>>> 3f57b00f289599c7383c33a09645384cd94a4d68
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByMember(Member member);
}
