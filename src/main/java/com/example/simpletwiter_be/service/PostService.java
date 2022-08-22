package com.example.simpletwiter_be.service;

import com.example.simpletwiter_be.domain.Member;
import com.example.simpletwiter_be.domain.Post;
import com.example.simpletwiter_be.dto.request.PostRequestDto;
import com.example.simpletwiter_be.dto.response.PostResponseDto;
import com.example.simpletwiter_be.dto.response.ResponseDto;
import com.example.simpletwiter_be.repository.PostRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Builder
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public ResponseDto<PostResponseDto> postPost(Member member, PostRequestDto postRequestDto){
        Post post = Post.builder()
                .contents(postRequestDto.getContents())
                .title(postRequestDto.getTitle())
                .imgUrl(postRequestDto.getImgUrl())
                .activate(true)
                .member(member)
                .build();
        Post returnPost = postRepository.save(post);
        return ResponseDto.success(PostResponseDto.builder()
                        .contents(returnPost.getContents())
                        .title(returnPost.getTitle())
                        .imgUrl(returnPost.getImgUrl())
                        .id(returnPost.getId())
                        .createdAt(returnPost.getCreatedAt().toLocalDate())
                        .modifiedAt(returnPost.getModifiedAt().toLocalDate())
                        .isMine(true)
                        .username(returnPost.getMember().getUsername())
                .build());
    }

    public ResponseDto<List<PostResponseDto>> getPostList(Member member, int page, int pageSize){
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        List<Post> postList = postRepository.findAllByActivateIsTrue(pageRequest);
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        for (Post post:postList){
            PostResponseDto postResponseDto = PostResponseDto.builder()
                .contents(post.getContents())
                .title(post.getTitle())
                .imgUrl(post.getImgUrl())
                .id(post.getId())
                .createdAt(post.getCreatedAt().toLocalDate())
                .modifiedAt(post.getModifiedAt().toLocalDate())
                .isMine(post.getMember().equals(member))
                .username(post.getMember().getUsername())
                .build();
            postResponseDtoList.add(postResponseDto);
        }
        return ResponseDto.success(postResponseDtoList);
    }

    public ResponseDto<PostResponseDto> getPostDetail(Member member, Long postId){
        Post post = postRepository.findByIdAndActivateIsTrue(postId).orElse(null);
        if (post == null){
            return ResponseDto.fail("게시글을 찾을 수 없습니다.");
        }else {
            return ResponseDto.success(PostResponseDto.builder()
                    .contents(post.getContents())
                    .title(post.getTitle())
                    .imgUrl(post.getImgUrl())
                    .id(post.getId())
                    .createdAt(post.getCreatedAt().toLocalDate())
                    .modifiedAt(post.getModifiedAt().toLocalDate())
                    .isMine(post.getMember().equals(member))
                    .username(post.getMember().getUsername())
                    .build());
        }
    }

    public ResponseDto<PostResponseDto> deletePost(Member member, Long postId){
        Post post = postRepository.findByIdAndActivateIsTrue(postId).orElse(null);
        if (post == null) {
            return ResponseDto.fail("게시글을 찾을 수 없습니다.");
        } else if (!post.getMember().equals(member)) {
            return ResponseDto.fail("자신이 작성한 게시글만 삭제할 수 있습니다.");
        }else {
            post.disable();
            return ResponseDto.success(null,"게시글 삭제 성공");
        }
    }

    public ResponseDto<PostResponseDto> putPost(Member member, Long postId, PostRequestDto postRequestDto){
        Post post = postRepository.findByIdAndActivateIsTrue(postId).orElse(null);
        if (post == null) {
            return ResponseDto.fail("게시글을 찾을 수 없습니다.");
        } else if (!post.getMember().equals(member)) {
            return ResponseDto.fail("자신이 작성한 게시글만 수정할 수 있습니다.");
        }else {
            post.update(postRequestDto.getTitle(), postRequestDto.getContents(), postRequestDto.getImgUrl());
            return ResponseDto.success(PostResponseDto.builder()
                    .contents(post.getContents())
                    .title(post.getTitle())
                    .imgUrl(post.getImgUrl())
                    .id(post.getId())
                    .createdAt(post.getCreatedAt().toLocalDate())
                    .modifiedAt(post.getModifiedAt().toLocalDate())
                    .isMine(post.getMember().equals(member))
                    .username(post.getMember().getUsername())
                    .build());
        }
    }

}
