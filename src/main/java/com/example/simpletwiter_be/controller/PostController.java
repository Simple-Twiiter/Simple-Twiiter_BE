package com.example.simpletwiter_be.controller;

import com.example.simpletwiter_be.dto.request.PostRequestDto;
import com.example.simpletwiter_be.dto.response.ResponseDto;
import com.example.simpletwiter_be.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final ObjectMapper objectMapper;

    @PostMapping
    public ResponseDto<?> postPost(HttpServletRequest request,
                                   @RequestPart("imgFile") MultipartFile multipartFile,
                                   @RequestPart("json") String json) throws Exception {
        PostRequestDto postRequestDto = objectMapper.readValue(json, PostRequestDto.class);
        return postService.postPost(request, postRequestDto, multipartFile);
    }

    @GetMapping
    public ResponseDto<?> getPostList(HttpServletRequest request,
                                      @RequestParam("page") int page,
                                      @RequestParam("pageSize") int pageSize){
        return postService.getPostList(request, page, pageSize);
    }

    @GetMapping("/{postId}")
    public ResponseDto<?> getPostDetail(HttpServletRequest request,
                                        @PathVariable("postId") Long postId){
        return postService.getPostDetail(request, postId);
    }

    @DeleteMapping("/{postId}")
    public ResponseDto<?> deletePost(HttpServletRequest request,
                                     @PathVariable("postId") Long postId){
        return postService.deletePost(request, postId);
    }

    @PutMapping("/{postId}")
    public  ResponseDto<?> putPost(HttpServletRequest request,
                                   @PathVariable("postId") Long postId,
                                   @RequestPart("imgFile") MultipartFile multipartFile,
                                   @RequestPart("json") String json) throws Exception {
        PostRequestDto postRequestDto = objectMapper.readValue(json, PostRequestDto.class);
        return postService.putPost(request, postId, postRequestDto, multipartFile);
    }
}
