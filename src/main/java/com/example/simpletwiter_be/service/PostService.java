package com.example.simpletwiter_be.service;

import com.example.simpletwiter_be.domain.Member;
import com.example.simpletwiter_be.domain.Post;
import com.example.simpletwiter_be.dto.request.PostRequestDto;
import com.example.simpletwiter_be.dto.response.PostResponseDto;
import com.example.simpletwiter_be.dto.response.ResponseDto;
import com.example.simpletwiter_be.repository.PostRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Builder
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
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
                        .createdAt(returnPost.getCreatedAt())
                        .modifiedAt(returnPost.getModifiedAt())
                        .isMine(true)
                        .username(returnPost.getMember().getUsername())
                .build());
    }

//    public ResponseDto<PostResponseDto> getPostList(Member member, int page, int pageSize){
//        return ResponseDto.success(PostResponseDto.builder()
//                .contents(contents)
//                .title(title)
//                .imgUrl(imgUrl)
//                .id()
//                .createdAt()
//                .modifiedAt()
//                .isMine()
//                .username(member.getUsername())
//                .build());
//    }
//    public ResponseDto<PostResponseDto> getPostDetail(Member member, Long postId){
//        return ResponseDto.success(PostResponseDto.builder()
//                .contents(contents)
//                .title(title)
//                .imgUrl(imgUrl)
//                .id()
//                .createdAt()
//                .modifiedAt()
//                .isMine()
//                .username(member.getUsername())
//                .build());
//    }
//    public ResponseDto<PostResponseDto> deletePost(Member member, Long postId){
//        return ResponseDto.success(PostResponseDto.builder()
//                .contents(contents)
//                .title(title)
//                .imgUrl(imgUrl)
//                .id()
//                .createdAt()
//                .modifiedAt()
//                .isMine()
//                .username(member.getUsername())
//                .build());
//    }
//    public ResponseDto<PostResponseDto> putPost(Member member, Long postId, PostRequestDto postRequestDto){
//        return ResponseDto.success(PostResponseDto.builder()
//                .contents(contents)
//                .title(title)
//                .imgUrl(imgUrl)
//                .id()
//                .createdAt()
//                .modifiedAt()
//                .isMine()
//                .username(member.getUsername())
//                .build());
//    }

}
