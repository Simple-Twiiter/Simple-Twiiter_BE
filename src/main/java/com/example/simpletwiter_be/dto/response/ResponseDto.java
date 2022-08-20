package com.example.simpletwiter_be.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto<T> {
    private final boolean result;
    private final T data;
    private final String message;

    public static <T> ResponseDto<T> success(T data){
        return new ResponseDto<>(true, data, null);
    }

    public static <T> ResponseDto<T> success(T data, String message){
        return new ResponseDto<>(true, data, message);
    }

    public static <T> ResponseDto<T> fail(String message){
        return new ResponseDto<>(false, null, message);
    }

}
