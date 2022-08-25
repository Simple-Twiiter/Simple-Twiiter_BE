package com.example.simpletwiter_be.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestDto {

    @NotBlank
    @Size(min = 4, max = 12)
    @Pattern(regexp = "^[a-zA-Z0-9]*${3,12}")
    private String username;

    @NotBlank
    @Size(min = 4, max = 32)
    @Pattern(regexp = "[a-z\\d]*${3,32}")
    private String password;

    @NotBlank
    private String passwordConfirm;

    private MultipartFile imgFile;


}

