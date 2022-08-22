package com.example.simpletwiter_be.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class PostResponseDto {
    private final String username;
    private final Long id;
    private final String title;
    private final String contents;
    private final String imgUrl;
    private final boolean isMine;
    private final LocalDate createdAt;
    private final LocalDate modifiedAt;
}
