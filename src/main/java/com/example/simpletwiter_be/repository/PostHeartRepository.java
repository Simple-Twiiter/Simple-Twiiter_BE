package com.example.simpletwiter_be.repository;


import com.example.simpletwiter_be.domain.Member;
import com.example.simpletwiter_be.domain.Post;
import com.example.simpletwiter_be.domain.PostHeart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostHeartRepository extends JpaRepository<PostHeart, Long> {

    Optional<PostHeart> findByPostAndMember(Post post, Member member);

    List<PostHeart> findByPost(Post post);
}
