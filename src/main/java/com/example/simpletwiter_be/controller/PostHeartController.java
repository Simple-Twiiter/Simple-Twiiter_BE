package com.example.simpletwiter_be.controller;

import com.example.simpletwiter_be.dto.response.ResponseDto;
import com.example.simpletwiter_be.service.PostHeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class PostHeartController {

    private final PostHeartService postHeartService;

    @PostMapping("/api/postLike/{postId}")
    public ResponseDto<?> postHeart(@PathVariable("postId") Long postId, HttpServletRequest httpServletRequest) {
        return postHeartService.createPostHeart(postId, httpServletRequest);
    }

    @DeleteMapping("/api/postLike/{postId}")
    public ResponseDto<?> postHeartDelete(@PathVariable("postId") Long postId, HttpServletRequest httpServletRequest) {
        return postHeartService.deletePostHeart(postId, httpServletRequest);
    }
}
