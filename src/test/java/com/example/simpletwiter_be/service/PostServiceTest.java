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
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
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
        assertEquals(null, responseDto.getData().getImgUrl());
        assertEquals(member.getUsername(), responseDto.getData().getUsername());
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
        assertEquals(member.getUsername(), responseDto.getData().getUsername());
    }

    @Test
    @DisplayName("게시글 목록 조회 성공")
    void getPostList1() {
        String title = "test getPostList 1";
        String contents = "test getPostList 1";
        List<Post> postList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Post post = Post.builder()
                    .contents(contents + i)
                    .title(title + i)
                    .member(member)
                    .activate(true)
                    .build();
            post.setCreatedAt(LocalDateTime.now());
            post.setModifiedAt(LocalDateTime.now());
            postList.add(post);
        }
        when(postRepository.findAllByActivateIsTrue(any(PageRequest.class))).thenReturn(postList.subList(0,20));

        ResponseDto<List<PostResponseDto>> responseDto= postService.getPostList(member, 0,20);
        assertTrue(responseDto.isResult());
        assertEquals(title + 0, responseDto.getData().get(0).getTitle());
        assertEquals(contents + 0, responseDto.getData().get(0).getContents());
        assertEquals(null, responseDto.getData().get(0).getImgUrl());
        assertEquals(member.getUsername(), responseDto.getData().get(0).getUsername());
        assertEquals(20, responseDto.getData().size());
    }

    @Test
    @DisplayName("게시글 상세 조회 성공")
    void getPostDetail1() {
        String title = "test title 1";
        String contents = "test contents 1";
        String imgUrl = "test imgUrl 1";
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
                .id(1L)
                .build();
        post.setCreatedAt(LocalDateTime.now());
        post.setModifiedAt(LocalDateTime.now());
        when(postRepository.findByIdAndActivateIsTrue(any(Long.class))).thenReturn(Optional.of(post));

        ResponseDto<PostResponseDto> responseDto= postService.getPostDetail(member,1L);
        assertTrue(responseDto.isResult());
        assertEquals(title, responseDto.getData().getTitle());
        assertEquals(contents, responseDto.getData().getContents());
        assertEquals(imgUrl, responseDto.getData().getImgUrl());
        assertEquals(member.getUsername(), responseDto.getData().getUsername());
    }

    @Test
    @DisplayName("게시글 상세 조회 실패")
    void getPostDetail2() {
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
                .id(1L)
                .build();
        post.setCreatedAt(LocalDateTime.now());
        post.setModifiedAt(LocalDateTime.now());
        when(postRepository.findByIdAndActivateIsTrue(any(Long.class))).thenReturn(Optional.empty());

        ResponseDto<PostResponseDto> responseDto= postService.getPostDetail(member,1L);
        assertFalse(responseDto.isResult());
        assertEquals("게시글을 찾을 수 없습니다.", responseDto.getMessage());
    }
    @Test
    @DisplayName("게시글 삭제 성공")
    void deletePost1() {
        String title = "test title 1";
        String contents = "test contents 1";
        String imgUrl = "test imgUrl 1";

        Post post = Post.builder()
                .contents(contents)
                .title(title)
                .imgUrl(imgUrl)
                .member(member)
                .activate(true)
                .id(1L)
                .build();
        post.setCreatedAt(LocalDateTime.now());
        post.setModifiedAt(LocalDateTime.now());
        when(postRepository.findByIdAndActivateIsTrue(any(Long.class))).thenReturn(Optional.of(post));

        ResponseDto<?> responseDto = postService.deletePost(member,1L);
        assertTrue(responseDto.isResult());
        assertNull(responseDto.getData());
        assertEquals("게시글 삭제 성공", responseDto.getMessage());
    }

    @Test
    @DisplayName("게시글 삭제 실패-게시글 조회 실패")
    void deletePost2() {
        String title = "test title 2";
        String contents = "test contents 2";
        String imgUrl = "test imgUrl 2";

        Post post = Post.builder()
                .contents(contents)
                .title(title)
                .imgUrl(imgUrl)
                .member(member)
                .activate(true)
                .id(1L)
                .build();
        post.setCreatedAt(LocalDateTime.now());
        post.setModifiedAt(LocalDateTime.now());
        when(postRepository.findByIdAndActivateIsTrue(any(Long.class))).thenReturn(Optional.empty());

        ResponseDto<?> responseDto = postService.deletePost(member,1L);
        assertFalse(responseDto.isResult());
        assertNull(responseDto.getData());
        assertEquals("게시글을 찾을 수 없습니다.", responseDto.getMessage());
    }

    @Test
    @DisplayName("게시글 삭제 실패-타인의 게시글")
    void deletePost3() {
        String title = "test title 2";
        String contents = "test contents 2";
        String imgUrl = "test imgUrl 2";

        Member otherMember = Member.builder()
                .username("otherUser")
                .build();

        Post post = Post.builder()
                .contents(contents)
                .title(title)
                .imgUrl(imgUrl)
                .member(otherMember)
                .activate(true)
                .id(1L)
                .build();
        post.setCreatedAt(LocalDateTime.now());
        post.setModifiedAt(LocalDateTime.now());
        when(postRepository.findByIdAndActivateIsTrue(any(Long.class))).thenReturn(Optional.of(post));

        ResponseDto<?> responseDto = postService.deletePost(member,1L);
        assertFalse(responseDto.isResult());
        assertNull(responseDto.getData());
        assertEquals("자신이 작성한 게시글만 삭제할 수 있습니다.", responseDto.getMessage());
    }
    @Test
    @DisplayName("게시글 수정 성공")
    void putPost1() {
        String title = "test title 2";
        String contents = "test contents 2";
        String imgUrl = "test imgUrl 2";

        Post post = Post.builder()
                .contents(contents)
                .title(title)
                .imgUrl(imgUrl)
                .member(member)
                .activate(true)
                .id(1L)
                .build();
        post.setCreatedAt(LocalDateTime.now());
        post.setModifiedAt(LocalDateTime.now());
        when(postRepository.findByIdAndActivateIsTrue(any(Long.class))).thenReturn(Optional.of(post));

        PostRequestDto postRequestDto = PostRequestDto.builder()
                .title("test title 3")
                .contents("test contents 3")
                .imgUrl("test imgUrl 3")
                .build();

        ResponseDto<PostResponseDto> responseDto = postService.putPost(member, 1L, postRequestDto);
        assertTrue(responseDto.isResult());
        assertEquals("test title 3", responseDto.getData().getTitle());
        assertEquals("test contents 3", responseDto.getData().getContents());
        assertEquals("test imgUrl 3",responseDto.getData().getImgUrl());

    }

    @Test
    @DisplayName("게시글 수정 실패-게시글 찾기 실패")
    void putPost2() {
        String title = "test title 2";
        String contents = "test contents 2";
        String imgUrl = "test imgUrl 2";

        Post post = Post.builder()
                .contents(contents)
                .title(title)
                .imgUrl(imgUrl)
                .member(member)
                .activate(true)
                .id(1L)
                .build();
        post.setCreatedAt(LocalDateTime.now());
        post.setModifiedAt(LocalDateTime.now());
        when(postRepository.findByIdAndActivateIsTrue(any(Long.class))).thenReturn(Optional.empty());

        PostRequestDto postRequestDto = PostRequestDto.builder()
                .title("test title 3")
                .contents("test contents 3")
                .imgUrl("test imgUrl 3")
                .build();

        ResponseDto<PostResponseDto> responseDto = postService.putPost(member, 1L, postRequestDto);
        assertFalse(responseDto.isResult());
        assertNull(responseDto.getData());
        assertEquals("게시글을 찾을 수 없습니다.", responseDto.getMessage());

    }
    @Test
    @DisplayName("게시글 수정 실패-타인의 게시글")
    void putPost3() {
        String title = "test title 2";
        String contents = "test contents 2";
        String imgUrl = "test imgUrl 2";

        Member otherMember = Member.builder()
                .username("otherUser")
                .build();

        Post post = Post.builder()
                .contents(contents)
                .title(title)
                .imgUrl(imgUrl)
                .member(otherMember)
                .activate(true)
                .id(1L)
                .build();
        post.setCreatedAt(LocalDateTime.now());
        post.setModifiedAt(LocalDateTime.now());
        when(postRepository.findByIdAndActivateIsTrue(any(Long.class))).thenReturn(Optional.of(post));

        PostRequestDto postRequestDto = PostRequestDto.builder()
                .title("test title 3")
                .contents("test contents 3")
                .imgUrl("test imgUrl 3")
                .build();

        ResponseDto<PostResponseDto> responseDto = postService.putPost(member, 1L, postRequestDto);
        assertFalse(responseDto.isResult());
        assertNull(responseDto.getData());
        assertEquals("자신이 작성한 게시글만 수정할 수 있습니다.", responseDto.getMessage());

    }
}