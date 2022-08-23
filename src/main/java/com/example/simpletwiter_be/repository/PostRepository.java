package com.example.simpletwiter_be.repository;

import com.example.simpletwiter_be.domain.Post;
import com.example.simpletwiter_be.domain.Users;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    int countByActivateIsTrueAndUsers(Users member);
    List<Post> findAllByActivateIsTrue(PageRequest pageRequest);
    Optional<Post> findByIdAndActivateIsTrue(Long id);
}
