package com.example.simpletwiter_be.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class ImageResponseDto {
    private final String imgUrl;
}
