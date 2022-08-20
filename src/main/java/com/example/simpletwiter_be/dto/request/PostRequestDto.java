package com.example.simpletwiter_be.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostRequestDto {
    private final String title;
    private final String contents;
    private final String imgUrl;
}