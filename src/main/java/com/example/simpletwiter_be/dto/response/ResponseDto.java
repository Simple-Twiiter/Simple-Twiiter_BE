package com.example.simpletwiter_be.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto<T> {
    private final boolean result;
    private final T data;
    private final String message;

    public ResponseDto<?> success(T data){
        return ResponseDto.builder()
                .result(true)
                .data(data)
                .build();
    }

    public ResponseDto<?> success(T data, String message){
        return ResponseDto.builder()
                .result(true)
                .data(data)
                .message(message)
                .build();
    }

    public ResponseDto<?> fail(String message){
        return ResponseDto.builder()
                .result(false)
                .message(message)
                .build();
    }

}
