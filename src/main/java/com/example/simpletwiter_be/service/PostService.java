package com.example.simpletwiter_be.service;

import com.example.simpletwiter_be.domain.Post;
import com.example.simpletwiter_be.dto.request.PostRequestDto;
import com.example.simpletwiter_be.dto.response.PostResponseDto;
import com.example.simpletwiter_be.dto.response.ResponseDto;
import com.example.simpletwiter_be.dto.response.UserDto;
import com.example.simpletwiter_be.repository.PostRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@Builder
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ImageUploadService imageUploadService;

    public ResponseDto<?> postPost(HttpServletRequest request, PostRequestDto postRequestDto, MultipartFile multipartFile) throws Exception {
        Users member = new Users();
        return postPost(member, postRequestDto, multipartFile);
    }

    public ResponseDto<PostResponseDto> postPost(Users member, PostRequestDto postRequestDto, MultipartFile multipartFile) throws Exception{
        String imgUrl = null;
        if (!multipartFile.isEmpty()){
            imgUrl = imageUploadService.uploadImage(multipartFile);
        }
        Post post = Post.builder()
                .contents(postRequestDto.getContents())
                .title(postRequestDto.getTitle())
                .imgUrl(imgUrl)
                .activate(true)
                .users(member)
                .build();
        Post returnPost = postRepository.save(post);
        UserDto userDto = UserDto.builder()
                .userImg(returnPost.getUsers().getUserImg())
                .username(returnPost.getUsers().getUsername())
                .build();
        return ResponseDto.success(PostResponseDto.builder()
                        .contents(returnPost.getContents())
                        .title(returnPost.getTitle())
                        .imgUrl(returnPost.getImgUrl())
                        .id(returnPost.getId())
                        .createdAt(returnPost.getCreatedAt().toLocalDate())
                        .modifiedAt(returnPost.getModifiedAt().toLocalDate())
                        .isMine(true)
                        .userDto(userDto)
                .build());
    }

    public ResponseDto<?> getPostList(HttpServletRequest request, int page, int pageSize){
        Users users=Users.builder().build();
        return getPostList(users, page, pageSize);
    }

    public ResponseDto<List<PostResponseDto>> getPostList(Users member, int page, int pageSize){
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        List<Post> postList = postRepository.findAllByActivateIsTrue(pageRequest);
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        for (Post post:postList){
            UserDto userDto = UserDto.builder()
                    .userImg(post.getUsers().getUserImg())
                    .username(post.getUsers().getUsername())
                    .build();
            PostResponseDto postResponseDto = PostResponseDto.builder()
                .contents(post.getContents())
                .title(post.getTitle())
                .imgUrl(post.getImgUrl())
                .id(post.getId())
                .createdAt(post.getCreatedAt().toLocalDate())
                .modifiedAt(post.getModifiedAt().toLocalDate())
                .isMine(post.getUsers().equals(member))
                .userDto(userDto)
                .build();
            postResponseDtoList.add(postResponseDto);
        }
        return ResponseDto.success(postResponseDtoList);
    }

    public ResponseDto<?> getPostDetail(HttpServletRequest request, Long postId){
        Users users= Users.builder().build();
        return getPostDetail(users, postId);
    }

    public ResponseDto<PostResponseDto> getPostDetail(Users member, Long postId){
        Post post = postRepository.findByIdAndActivateIsTrue(postId).orElse(null);
        if (post == null){
            return ResponseDto.fail("게시글을 찾을 수 없습니다.");
        }else {
            UserDto userDto = UserDto.builder()
                    .userImg(post.getUsers().getUserImg())
                    .username(post.getUsers().getUsername())
                    .build();
            return ResponseDto.success(PostResponseDto.builder()
                    .contents(post.getContents())
                    .title(post.getTitle())
                    .imgUrl(post.getImgUrl())
                    .id(post.getId())
                    .createdAt(post.getCreatedAt().toLocalDate())
                    .modifiedAt(post.getModifiedAt().toLocalDate())
                    .isMine(post.getUsers().equals(member))
                    .userDto(userDto)
                    .build());
        }
    }

    public ResponseDto<PostResponseDto> deletePost(HttpServletRequest request, Long postId){
        Users users = Users.builder().build();
        return deletePost(users, postId);
    }
    public ResponseDto<PostResponseDto> deletePost(Users member, Long postId){
        Post post = postRepository.findByIdAndActivateIsTrue(postId).orElse(null);
        if (post == null) {
            return ResponseDto.fail("게시글을 찾을 수 없습니다.");
        } else if (!post.getUsers().equals(member)) {
            return ResponseDto.fail("자신이 작성한 게시글만 삭제할 수 있습니다.");
        }else {
            post.disable();
            return ResponseDto.success(null,"게시글 삭제 성공");
        }
    }

    public ResponseDto<PostResponseDto> putPost(HttpServletRequest request, Long postId, PostRequestDto postRequestDto, MultipartFile multipartFile) throws Exception {
        Users users = Users.builder().build();
        return putPost(users, postId, postRequestDto, multipartFile);
    }
    public ResponseDto<PostResponseDto> putPost(Users member, Long postId, PostRequestDto postRequestDto, MultipartFile multipartFile) throws Exception {
        Post post = postRepository.findByIdAndActivateIsTrue(postId).orElse(null);
        if (post == null) {
            return ResponseDto.fail("게시글을 찾을 수 없습니다.");
        } else if (!post.getUsers().equals(member)) {
            return ResponseDto.fail("자신이 작성한 게시글만 수정할 수 있습니다.");
        }else {
            String imgUrl = null;
            if (!multipartFile.isEmpty()){
                imgUrl = imageUploadService.uploadImage(multipartFile);
            }
            post.update(postRequestDto.getTitle(), postRequestDto.getContents(), imgUrl);
            UserDto userDto = UserDto.builder()
                    .userImg(post.getUsers().getUserImg())
                    .username(post.getUsers().getUsername())
                    .build();
            return ResponseDto.success(PostResponseDto.builder()
                    .contents(post.getContents())
                    .title(post.getTitle())
                    .imgUrl(post.getImgUrl())
                    .id(post.getId())
                    .createdAt(post.getCreatedAt().toLocalDate())
                    .modifiedAt(post.getModifiedAt().toLocalDate())
                    .isMine(post.getUsers().equals(member))
                    .userDto(userDto)
                    .build());
        }
    }

}
