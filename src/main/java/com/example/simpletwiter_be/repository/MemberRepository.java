package com.example.simpletwiter_be.repository;

import com.example.simpletwiter_be.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findById(Long id);
<<<<<<< HEAD

    Optional<Member> findByUsername(String username);


=======
    Optional<Member> findByUsername(String username);
>>>>>>> d7a23d7ab51977f0a7b17aef99807234a536d14f
}
