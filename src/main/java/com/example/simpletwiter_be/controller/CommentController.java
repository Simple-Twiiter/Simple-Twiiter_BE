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

    @RequestMapping(value = "/api/comment/{postId}", method = RequestMethod.POST)
    public ResponseDto<?> createComment(@PathVariable Long postId,@RequestBody CommentRequestDto requestDto, HttpServletRequest request) {
        return commentService.createComment(postId,requestDto, request);
    }
    @RequestMapping(value = "/api/comment/{id}", method = RequestMethod.GET)
        public ResponseDto<?> getAllComments(@PathVariable Long id, HttpServletRequest request){
            return commentService.getAllCommentsByPost(id, request);
    }

    @RequestMapping(value = "/api/comment/{commentId}/{postId}", method = RequestMethod.PUT)
    public ResponseDto<?> updateComment(@PathVariable Long commentId,@PathVariable Long postId,@RequestBody CommentRequestDto requestDto,
                                        HttpServletRequest request) {
        return commentService.updateComment(postId, commentId,requestDto, request);
    }

    @RequestMapping(value = "/api/comment/{commentId}", method = RequestMethod.DELETE)
    public ResponseDto<?> deleteComment(@PathVariable Long commentId, HttpServletRequest request) {
        return commentService.deleteComment(commentId, request);
    }
}