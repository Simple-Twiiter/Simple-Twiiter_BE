package com.example.simpletwiter_be.service;


import com.example.simpletwiter_be.domain.Member;
import com.example.simpletwiter_be.domain.Post;
import com.example.simpletwiter_be.domain.PostHeart;
import com.example.simpletwiter_be.dto.response.ResponseDto;
import com.example.simpletwiter_be.jwt.TokenProvider;
import com.example.simpletwiter_be.repository.PostHeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PostHeartService {

    private final TokenProvider tokenProvider;
    private final PostService postService;
    private final PostHeartRepository postHeartRepository;


    @Transactional
    public ResponseDto<?> createPostHeart(Long id, HttpServletRequest request) {

        if (null == request.getHeader("RefreshToken")) {
            return ResponseDto.fail("로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("로그인이 필요합니다.");
        }

        Member member = validateMember(request);

        if (null == member) {
            return ResponseDto.fail("Token이 유효하지 않습니다.");
        }

        Post post = postService.isPresentPost(id);

        if (null == post) {
            return ResponseDto.fail("존재하지 않는 게시글 id 입니다.");
        }
        //게시글 좋아요한 사람 0

        Optional<PostHeart> postHeartOptional = postHeartRepository.findByPostAndMember(post, member);
        if (postHeartOptional.isPresent()) {
            return ResponseDto.fail("이미 좋아요를 누르셨습니다.");
        }

        if (!postHeartOptional.isPresent()) {
//            post.update(post.getHeartCount());
            PostHeart postHeart = PostHeart.builder()
                    .postHeart(1)
                    .member(member)
                    .post(post)
                    .build();
            postHeartRepository.save(postHeart);
            List<PostHeart> postHearts = postHeartRepository.findByPost(post);
            post.update(postHearts);

        }
        return ResponseDto.success("좋아요");
    }

    @Transactional
    public ResponseDto<?> deletePostHeart(Long id, HttpServletRequest request) {

        if (null == request.getHeader("RefreshToken")) {
            return ResponseDto.fail("로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("로그인이 필요합니다.");
        }

        Member member = validateMember(request);

        if (null == member) {
            return ResponseDto.fail("Token이 유효하지 않습니다.");
        }

        Post post = postService.isPresentPost(id);

        if (null == post) {
            return ResponseDto.fail("존재하지 않는 게시글 id 입니다.");
        }
        //게시글 좋아요한 사람 0

        Optional<PostHeart> postHeartOptional = postHeartRepository.findByPostAndMember(post, member);
        if (!postHeartOptional.isPresent()) {
            return ResponseDto.fail("좋아요를 누르지 않으셨습니다.");
        }
        postHeartRepository.delete(postHeartOptional.get());
        List<PostHeart> postHearts = postHeartRepository.findByPost(post);
        post.update(postHearts);
        return ResponseDto.success("좋아요 취소");
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("RefreshToken"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }
}
