package com.example.simpletwiter_be.shared;

import com.example.simpletwiter_be.domain.Member;
import com.example.simpletwiter_be.jwt.TokenProvider;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class UserGetterFromToken {
    private final TokenProvider tokenProvider;

    public Result UserGetterFromToken(HttpServletRequest request){
        if (null == request.getHeader("Refresh-Token")) {
            return new Result(null, "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return new Result(null, "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return new Result(null, "Token이 유효하지 않습니다.");
        }
        return new Result(member, null);

    }

    private Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }

    @Data
    public static class Result{
        private final Member member;
        private final String message;
    }
}
