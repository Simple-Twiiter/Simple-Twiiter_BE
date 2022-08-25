package com.example.simpletwiter_be.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {
    private String username;
    private String userImg;
    private Boolean isFollow;

    @Builder
    public UserDto(String username, String userImg, boolean isFollow){
        this.userImg=userImg;
        this.username=username;
        this.isFollow= isFollow;
    }
}
