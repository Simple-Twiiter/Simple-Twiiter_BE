package com.example.simpletwiter_be.dto.response;

import com.example.simpletwiter_be.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {

    private Long id;
    private UserDto member;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private boolean isMine;


}
