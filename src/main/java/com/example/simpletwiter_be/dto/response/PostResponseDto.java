package com.example.simpletwiter_be.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PostResponseDto {
//    private final String username;
    private final UserDto member;
    private final Long id;
    private final String title;
    private final String contents;
    private final String imgUrl;
    private final Boolean isMine;
    private final Boolean isLike;
    private int heartCount;
    private final LocalDate createdAt;
    private final LocalDate modifiedAt;
}
