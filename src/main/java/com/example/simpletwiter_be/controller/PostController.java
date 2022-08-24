package com.example.simpletwiter_be.controller;

import com.example.simpletwiter_be.domain.Member;
import com.example.simpletwiter_be.dto.request.PostRequestDto;
import com.example.simpletwiter_be.dto.response.PostResponseDto;
import com.example.simpletwiter_be.dto.response.ResponseDto;
import com.example.simpletwiter_be.service.PostService;
import com.example.simpletwiter_be.shared.UserGetterFromToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final ObjectMapper objectMapper;
    private final UserGetterFromToken userGetterFromToken;

    @PostMapping
    public ResponseDto<?> postPost(HttpServletRequest request,
                                   @RequestPart("imgFile") MultipartFile multipartFile,
                                   @RequestPart("json") PostRequestDto postRequestDto) {
        Function<Member, ResponseDto<?>> fn = (Member member) -> {
            try {
                return postService.postPost(member, postRequestDto, multipartFile);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
        return userGetterFromToken.applyPostService(request, fn);
    }

    @GetMapping
    public ResponseDto<?> getPostList(HttpServletRequest request,
                                      @RequestParam("page") int page,
                                      @RequestParam("pageSize") int pageSize){
        Function<Member, ResponseDto<?>> fn = (Member member) -> {
            try {
                return postService.getPostList(member, page, pageSize);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
        return userGetterFromToken.applyPostService(request, fn);
    }

    @GetMapping("/{postId}")
    public ResponseDto<?> getPostDetail(HttpServletRequest request,
                                        @PathVariable("postId") Long postId){
        Function<Member, ResponseDto<?>> fn = (Member member) -> {
            try {
                return postService.getPostDetail(member, postId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
        return userGetterFromToken.applyPostService(request, fn);
    }

    @DeleteMapping("/{postId}")
    public ResponseDto<?> deletePost(HttpServletRequest request,
                                     @PathVariable("postId") Long postId){
        Function<Member, ResponseDto<?>> fn = (Member member) -> {
            try {
                return postService.deletePost(member, postId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
        return userGetterFromToken.applyPostService(request, fn);
    }

    @PutMapping("/{postId}")
    public  ResponseDto<?> putPost(HttpServletRequest request,
                                   @PathVariable("postId") Long postId,
                                   @RequestPart("imgFile") MultipartFile multipartFile,
                                   @RequestPart("json") String json) throws Exception {
        PostRequestDto postRequestDto = objectMapper.readValue(json, PostRequestDto.class);

        Function<Member, ResponseDto<?>> fn = (Member member) -> {
            try {
                return postService.putPost(member, postId, postRequestDto, multipartFile);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
        return userGetterFromToken.applyPostService(request, fn);
    }
}
