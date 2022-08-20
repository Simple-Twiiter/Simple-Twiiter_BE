package com.example.simpletwiter_be.repository;

import com.example.simpletwiter_be.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
