package com.example.simpletwiter_be.controller;

import com.example.simpletwiter_be.domain.Member;
import com.example.simpletwiter_be.dto.request.CommentRequestDto;
import com.example.simpletwiter_be.dto.response.ResponseDto;
import com.example.simpletwiter_be.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Validated
@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;

    @RequestMapping(value = "/api/comment", method = RequestMethod.POST)
    public ResponseDto<?> createComment(@RequestBody CommentRequestDto requestDto, HttpServletRequest request) {
        return commentService.createComment(requestDto.getPostId(), requestDto, request);
    }
    @RequestMapping(value = "/api/comment/{id}", method = RequestMethod.GET)
        public ResponseDto<?> getAllComments(@RequestParam(value = "pageSize", defaultValue = "50", required = false) int pageSize,
                                             @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                             @RequestBody CommentRequestDto requestDto, Member member){
            return commentService.getAllCommentsByPost(requestDto.getPostId(), member,pageSize, page);
    }

    @RequestMapping(value = "/api/comment/{commentId}", method = RequestMethod.PUT)
    public ResponseDto<?> updateComment(@PathVariable Long commentId,@RequestBody CommentRequestDto requestDto,
                                        HttpServletRequest request) {
        return commentService.updateComment(requestDto.getPostId(), commentId,requestDto, request);
    }

    @RequestMapping(value = "/api/comment/{commentId}", method = RequestMethod.DELETE)
    public ResponseDto<?> deleteComment(@PathVariable Long commentId, HttpServletRequest request) {
        return commentService.deleteComment(commentId, request);
    }
}