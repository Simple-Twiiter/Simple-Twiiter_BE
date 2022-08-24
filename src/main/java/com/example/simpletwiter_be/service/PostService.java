package com.example.simpletwiter_be.service;

import com.example.simpletwiter_be.domain.Post;
import com.example.simpletwiter_be.domain.Member;
import com.example.simpletwiter_be.domain.PostHeart;
import com.example.simpletwiter_be.dto.request.PostRequestDto;
import com.example.simpletwiter_be.dto.response.PostResponseDto;
import com.example.simpletwiter_be.dto.response.ResponseDto;
import com.example.simpletwiter_be.dto.response.UserDto;
import com.example.simpletwiter_be.repository.PostHeartRepository;
import com.example.simpletwiter_be.repository.PostRepository;
import com.example.simpletwiter_be.shared.UserGetterFromToken;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Builder
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostHeartRepository postHeartRepository;
    private final ImageUploadService imageUploadService;
    private final UserGetterFromToken userGetterFromToken;


    @Transactional
    public ResponseDto<?> postPost(Member member, PostRequestDto postRequestDto, MultipartFile multipartFile) throws Exception{
        String imgUrl = null;
        if (!multipartFile.isEmpty()){
            imgUrl = imageUploadService.uploadImage(multipartFile);
        }
        Post post = Post.builder()
                .contents(postRequestDto.getContents())
                .title(postRequestDto.getTitle())
                .imgUrl(imgUrl)
                .activate(true)
                .member(member)
                .build();
        Post returnPost = postRepository.save(post);
        UserDto userDto = UserDto.builder()
                .userImg(returnPost.getMember().getUserImg())
                .username(returnPost.getMember().getUsername())
                .isFollow(false)
                .build();
        return ResponseDto.success(PostResponseDto.builder()
                        .contents(returnPost.getContents())
                        .title(returnPost.getTitle())
                        .imgUrl(returnPost.getImgUrl())
                        .id(returnPost.getId())
                        .createdAt(returnPost.getCreatedAt().toLocalDate())
                        .modifiedAt(returnPost.getModifiedAt().toLocalDate())
                        .isMine(true)
                        .isLike(false)
                        .heartCount(0)
                        .member(userDto)
                .build());
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> getPostList(Member member, int page, int pageSize){
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        List<Post> postList = postRepository.findAllByActivateIsTrue(pageRequest);
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        for (Post post:postList){
            UserDto userDto = UserDto.builder()
                    .userImg(post.getMember().getUserImg())
                    .username(post.getMember().getUsername())
                    .isFollow(false)
                    .build();
            PostHeart postHeart = postHeartRepository.findByPostAndMember(post,member).orElse(null);
            boolean isLike = postHeart != null && postHeart.getPostHeart()==1;
            PostResponseDto postResponseDto = PostResponseDto.builder()
                    .contents(post.getContents())
                    .title(post.getTitle())
                    .imgUrl(post.getImgUrl())
                    .id(post.getId())
                    .createdAt(post.getCreatedAt().toLocalDate())
                    .modifiedAt(post.getModifiedAt().toLocalDate())
                    .isMine(post.getMember().equals(member))
                    .isLike(isLike)
                    .member(userDto)
                    .heartCount(post.getHeartCount())
                    .build();
            postResponseDtoList.add(postResponseDto);
        }
        return ResponseDto.success(postResponseDtoList);
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> getPostDetail(Member member, Long postId){
        Post post = postRepository.findByIdAndActivateIsTrue(postId).orElse(null);
        if (post == null){
            return ResponseDto.fail("게시글을 찾을 수 없습니다.");
        }else {
            UserDto userDto = UserDto.builder()
                    .userImg(post.getMember().getUserImg())
                    .username(post.getMember().getUsername())
                    .isFollow(false)
                    .build();
            PostHeart postHeart = postHeartRepository.findByPostAndMember(post,member).orElse(null);
            boolean isLike = postHeart != null && postHeart.getPostHeart()==1;
            return ResponseDto.success(PostResponseDto.builder()
                    .contents(post.getContents())
                    .title(post.getTitle())
                    .imgUrl(post.getImgUrl())
                    .id(post.getId())
                    .createdAt(post.getCreatedAt().toLocalDate())
                    .modifiedAt(post.getModifiedAt().toLocalDate())
                    .isMine(post.getMember().equals(member))
                    .isLike(isLike)
                    .member(userDto)
                    .heartCount(post.getHeartCount())
                    .build());
        }
    }

    @Transactional
    public ResponseDto<?> deletePost(Member member, Long postId){
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

    @Transactional
    public ResponseDto<?> putPost(Member member, Long postId, PostRequestDto postRequestDto, MultipartFile multipartFile) throws Exception {
        Post post = postRepository.findByIdAndActivateIsTrue(postId).orElse(null);
        if (post == null) {
            return ResponseDto.fail("게시글을 찾을 수 없습니다.");
        } else if (!post.getMember().equals(member)) {
            return ResponseDto.fail("자신이 작성한 게시글만 수정할 수 있습니다.");
        }else {
            String imgUrl = null;
            if (!multipartFile.isEmpty()){
                imgUrl = imageUploadService.uploadImage(multipartFile);
            }
            post.update(postRequestDto.getTitle(), postRequestDto.getContents(), imgUrl);
            UserDto userDto = UserDto.builder()
                    .userImg(post.getMember().getUserImg())
                    .username(post.getMember().getUsername())
                    .isFollow(false)
                    .build();
            PostHeart postHeart = postHeartRepository.findByPostAndMember(post,member).orElse(null);
            boolean isLike = postHeart != null && postHeart.getPostHeart()==1;
            return ResponseDto.success(PostResponseDto.builder()
                    .contents(post.getContents())
                    .title(post.getTitle())
                    .imgUrl(post.getImgUrl())
                    .id(post.getId())
                    .createdAt(post.getCreatedAt().toLocalDate())
                    .modifiedAt(post.getModifiedAt().toLocalDate())
                    .isMine(post.getMember().equals(member))
                    .isLike(isLike)
                    .member(userDto)
                            .heartCount(post.getHeartCount())
                    .build());
        }
    }
    @Transactional(readOnly = true)
    public Post isPresentPost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        return optionalPost.orElse(null);
    }

}
