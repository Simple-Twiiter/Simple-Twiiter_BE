package com.example.simpletwiter_be.service;

import antlr.Token;
import com.example.simpletwiter_be.domain.Comment;
import com.example.simpletwiter_be.domain.Post;
import com.example.simpletwiter_be.domain.Users;
import com.example.simpletwiter_be.dto.request.CommentRequestDto;
import com.example.simpletwiter_be.dto.response.CommentResponseDto;
import com.example.simpletwiter_be.dto.response.ResponseDto;
import com.example.simpletwiter_be.dto.response.UserDto;
import com.example.simpletwiter_be.jwt.TokenProvider;
import com.example.simpletwiter_be.repository.CommentRepository;
import com.example.simpletwiter_be.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.hql.internal.ast.util.TokenPrinters;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final TokenProvider tokenProvider;
    private final PostService postService;
    private PostRepository postRepository;

    @Transactional
    public ResponseDto<?> createComment(Long postId, CommentRequestDto requestDto, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("로그인이 필요합니다.");
        }

        Users member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("Token이 유효하지 않습니다.");
        }

        Post post = postRepository.findById(postId).orElse(null);
        if (post == null){
            return ResponseDto.fail("해당 게시글을 찾을 수 없습니다.");
        }

        Comment comment = Comment.builder()
                .member(member)
                .post(post)
                .content(requestDto.getContent())
                .build();

        commentRepository.save(comment);

        return ResponseDto.success(
                CommentResponseDto.builder()
                        .id(comment.getId())

                        .content(comment.getContent())
                        .createdAt(comment.getCreatedAt())
                        .modifiedAt(comment.getModifiedAt())
                        .isMine(true)
                        .build()

        );
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> getAllCommentsByPost(Long postId, Users member, int page, int pageSize) {
        Post post = postRepository.findById(postId).orElse(null);
        PageRequest pageRequest = PageRequest.of(page,pageSize);
        if (post == null){
            return ResponseDto.fail("해당 게시글을 찾을 수 없습니다.");
        }

        List<Comment> commentList = commentRepository.findAllByPost(post,pageRequest);
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

        for (Comment comment : commentList) {

            UserDto userDto = new UserDto(member.getUsername(), member.getUserImg());


            commentResponseDtoList.add(
                    CommentResponseDto.builder()
                            .id(comment.getId())
                            .content(comment.getContent())
                            .member(userDto)
                            .createdAt(comment.getCreatedAt())
                            .modifiedAt(comment.getModifiedAt())
                            .isMine(post.getUsers().equals(member))
                            .build()
            );
        }

        return ResponseDto.success(commentResponseDtoList);
    }

    @Transactional
    public ResponseDto<?> updateComment(Long postId , Long id, CommentRequestDto requestDto, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("로그인이 필요합니다.");
        }

        Users member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("Token이 유효하지 않습니다.");
        }

        Post post = postRepository.findById(postId).orElse(null);

        if (null == post) {
            return ResponseDto.fail("존재하지 않는 게시글 id 입니다.");
        }

        Comment comment = isPresentComment(id);

        if (null == comment) {
            return ResponseDto.fail("존재하지 않는 댓글 id 입니다.");
        }

        if (comment.validateMember(member)) {
            return ResponseDto.fail("작성자만 수정할 수 있습니다.");
        }

        comment.update(requestDto);
        return ResponseDto.success(
                CommentResponseDto.builder()
                        .id(comment.getId())

                        .content(comment.getContent())
                        .createdAt(comment.getCreatedAt())
                        .modifiedAt(comment.getModifiedAt())
                        .isMine(post.getUsers().equals(member))
                        .build()
        );
    }

    @Transactional
    public ResponseDto<?> deleteComment(Long id, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("로그인이 필요합니다.");
        }

        Users member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("Token이 유효하지 않습니다.");
        }

        Comment comment = isPresentComment(id);
        if (null == comment) {
            return ResponseDto.fail("존재하지 않는 댓글 id 입니다.");
        }

        if (comment.validateMember(member)) {
            return ResponseDto.fail("작성자만 삭제할 수 있습니다.");
        }

        commentRepository.delete(comment);
        return ResponseDto.success("success");
    }

    @Transactional(readOnly = true)
    public Comment isPresentComment(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        return optionalComment.orElse(null);
    }

    @Transactional
    public Users validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }
}

