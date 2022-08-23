package com.example.simpletwiter_be.repository;

import com.example.simpletwiter_be.domain.Comment;
import com.example.simpletwiter_be.domain.Follow;
import com.example.simpletwiter_be.domain.Post;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow,Long> {

    List<Follow> findAllByFollow(Follow follow);

}
