package com.example.simpletwiter_be.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRequestDto {

    @Getter
    @Setter
    public static class SignUp {

        @NotBlank
        @Size(min = 1, max = 12)
        @Pattern(regexp = "[a-zA-Z\\d]*${1,12}")
        private String username;

        @NotBlank
        @NotEmpty(message = "비밀번호는 필수 입력값입니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        private String password;

//        @NotBlank
//        private String passwordConfirm;
    }
    @Getter
    @Setter
    public static class Login {
        @NotEmpty(message = "아이디는 필수 입력값입니다.")
        @Pattern(regexp = "[a-zA-Z\\d]*${3,12}", message = "아이디 형식에 맞지 않습니다.")
        private String username;

        @NotEmpty(message = "비밀번호는 필수 입력값입니다.")
        private String password;

        public UsernamePasswordAuthenticationToken toAuthentication() {
            return new UsernamePasswordAuthenticationToken(username, password);
        }
    }
    @Getter
    @Setter
    public static class Logout {
        @NotEmpty(message = "잘못된 요청입니다.")
        private String accessToken;

        @NotEmpty(message = "잘못된 요청입니다.")
        private String refreshToken;
    }
}
