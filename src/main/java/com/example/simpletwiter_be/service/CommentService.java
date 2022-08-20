package com.example.simpletwiter_be.service;

import antlr.Token;
import com.example.simpletwiter_be.domain.Member;
import com.example.simpletwiter_be.domain.Post;
import com.example.simpletwiter_be.dto.request.CommentRequestDto;
import com.example.simpletwiter_be.dto.response.ResponseDto;
import com.example.simpletwiter_be.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.hibernate.hql.internal.ast.util.TokenPrinters;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class CommentService {

    private TokenProvider tokenProvider;

    @Transactional
    public ResponseDto<?> createCommen(CommentRequestDto requestDto, HttpServletRequest request){
        if (null== request.getHeader("Refresh-Token")){
            return ResponseDto.fail("로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")){
            return ResponseDto.fail("로그인이 필요합니다.");
        }
        Member member =validateMember(request);
        if (null == member){
            return ResponseDto.fail("Token이 유효하지 않습니다.");
        }
//        Post post = postService.isPresentPost(requestDto.getPostId());
//        if (null == post) {
//            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
//    }
    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
}
