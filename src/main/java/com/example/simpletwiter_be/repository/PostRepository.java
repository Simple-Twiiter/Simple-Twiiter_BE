package com.example.simpletwiter_be.repository;

import com.example.simpletwiter_be.domain.Post;
<<<<<<< HEAD

=======
import com.example.simpletwiter_be.domain.Member;
>>>>>>> 3f57b00f289599c7383c33a09645384cd94a4d68
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    int countByActivateIsTrueAndUsers(Member member);
    List<Post> findAllByActivateIsTrue(PageRequest pageRequest);
    Optional<Post> findByIdAndActivateIsTrue(Long id);
}
