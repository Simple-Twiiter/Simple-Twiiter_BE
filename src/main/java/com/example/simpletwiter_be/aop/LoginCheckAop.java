package com.example.simpletwiter_be.aop;

import com.example.simpletwiter_be.dto.response.ResponseDto;
import com.example.simpletwiter_be.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@RequiredArgsConstructor
@Component
public class LoginCheckAop {

    private final TokenProvider tokenProvider;

    @Before("@annotation(com.one.mycodi.annotation.LoginCheck)")
    public ResponseDto<?> loginCheck(){

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();

        String message = "";
        String code = "";
        if (null == request.getHeader("RefreshToken")) {
            message = "MEMBER_NOT_FOUND";

        }

        if (null == request.getHeader("Authorization")) {
            message = "MEMBER_NOT_FOUND";

        }

        return ResponseDto.fail(message);
    }

}





