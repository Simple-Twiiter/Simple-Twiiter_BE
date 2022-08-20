package com.example.simpletwiter_be.service;

import com.example.simpletwiter_be.domain.Member;
import com.example.simpletwiter_be.domain.Post;
import com.example.simpletwiter_be.dto.request.PostRequestDto;
import com.example.simpletwiter_be.dto.response.PostResponseDto;
import com.example.simpletwiter_be.dto.response.ResponseDto;
import com.example.simpletwiter_be.repository.PostRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    private static Member member;

    @BeforeAll
    static void setUp(){
        member = Member.builder()
                .username("test")
                .password("test")
                .userImg("")
                .id(1L)
                .build();

    }

    @Test
    @DisplayName("게시글 생성 성공-No img")
    void postPost1() {
        String title = "test title 1";
        String contents = "test contents 1";
        PostRequestDto postRequestDto = PostRequestDto.builder()
                .contents(contents)
                .title(title)
                .build();

        Post post = Post.builder()
                .contents(postRequestDto.getContents())
                .title(postRequestDto.getTitle())
                .imgUrl(postRequestDto.getImgUrl())
                .member(member)
                .activate(true)
                .build();
        post.setCreatedAt(LocalDateTime.now());
        post.setModifiedAt(LocalDateTime.now());
        when(postRepository.save(any(Post.class))).thenReturn(post);

        ResponseDto<PostResponseDto> responseDto= postService.postPost(member,postRequestDto);
        assertTrue(responseDto.isResult());
        assertEquals(title, responseDto.getData().getTitle());
        assertEquals(contents, responseDto.getData().getContents());
    }

    @Test
    @DisplayName("게시글 생성 성공-include img")
    void postPost2() {
        String title = "test title 2";
        String contents = "test contents 2";
        String imgUrl = "test imgUrl 2";
        PostRequestDto postRequestDto = PostRequestDto.builder()
                .contents(contents)
                .title(title)
                .imgUrl(imgUrl)
                .build();

        Post post = Post.builder()
                .contents(postRequestDto.getContents())
                .title(postRequestDto.getTitle())
                .imgUrl(postRequestDto.getImgUrl())
                .member(member)
                .activate(true)
                .build();
        post.setCreatedAt(LocalDateTime.now());
        post.setModifiedAt(LocalDateTime.now());
        when(postRepository.save(any(Post.class))).thenReturn(post);

        ResponseDto<PostResponseDto> responseDto= postService.postPost(member,postRequestDto);
        assertTrue(responseDto.isResult());
        assertEquals(title, responseDto.getData().getTitle());
        assertEquals(contents, responseDto.getData().getContents());
        assertEquals(imgUrl, responseDto.getData().getImgUrl());
    }

    @Test
    void getPostList() {
    }

    @Test
    void getPostDetail() {
    }

    @Test
    void deletePost() {
    }

    @Test
    void putPost() {
    }
}