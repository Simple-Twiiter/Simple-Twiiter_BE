package com.example.simpletwiter_be.dto.request;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostRequestDto {
    @NotNull
    private final String title;
    @NotNull
    private final String contents;
}