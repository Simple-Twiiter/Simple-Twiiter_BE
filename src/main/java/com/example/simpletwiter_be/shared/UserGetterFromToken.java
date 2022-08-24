package com.example.simpletwiter_be.shared;

import com.example.simpletwiter_be.domain.Member;
import com.example.simpletwiter_be.dto.response.ResponseDto;
import com.example.simpletwiter_be.jwt.TokenProvider;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.function.Function;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class UserGetterFromToken {
    private final TokenProvider tokenProvider;

    public ResponseDto<?> applyPostService(HttpServletRequest request, Function<Member,ResponseDto<?>> fn){
        if (null == request.getHeader("RefreshToken")) {
//            return new Result(null, "로그인이 필요합니다.");
            return ResponseDto.fail("123로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
//            return new Result(null, "로그인이 필요합니다.");
            return ResponseDto.fail("456로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
//            return new Result(null, "Token이 유효하지 않습니다.");
            return ResponseDto.fail("Token이 유효하지 않습니다.");
        }
        return fn.apply(member);

    }

    private Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("RefreshToken"))) {
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
