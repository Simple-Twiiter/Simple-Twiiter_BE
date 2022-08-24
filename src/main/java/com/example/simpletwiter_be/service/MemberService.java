package com.example.simpletwiter_be.service;

import com.example.simpletwiter_be.domain.Member;
import com.example.simpletwiter_be.dto.request.LoginRequestDto;
import com.example.simpletwiter_be.dto.request.MemberRequestDto;
import com.example.simpletwiter_be.dto.request.TokenDto;
import com.example.simpletwiter_be.dto.response.MemberResponseDto;
import com.example.simpletwiter_be.dto.response.ResponseDto;
import com.example.simpletwiter_be.jwt.TokenProvider;
import com.example.simpletwiter_be.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final ImageUploadService imageUploadService;



    MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider, ImageUploadService imageUploadService){
        this.memberRepository = memberRepository;
        this. passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.imageUploadService = imageUploadService;
    }

    @Transactional
    public ResponseDto<?> createMember(MemberRequestDto requestDto, MultipartFile multipartFile) throws Exception {
        String userImg = null;
        if (null != isPresentMember(requestDto.getUsername())) {
            return ResponseDto.fail("중복된 닉네임 입니다.");
        }

        if (!requestDto.getPassword().equals(requestDto.getPasswordConfirm())) {
            return ResponseDto.fail("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }
        if (!multipartFile.isEmpty()){
            userImg = imageUploadService.uploadImage(multipartFile);
        }
        
        Member member = Member.builder()
                .username(requestDto.getUsername())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .userImg(userImg)
                .build();
        memberRepository.save(member);
        return ResponseDto.success(
                MemberResponseDto.builder()
                        .id(member.getId())
                        .username(member.getUsername())
                        .userImg(member.getUserImg())
                        .createdAt(member.getCreatedAt())
                        .modifiedAt(member.getModifiedAt())
                        .build()
        );
    }

    @Transactional
    public ResponseDto<?> login(LoginRequestDto requestDto, HttpServletResponse response) {
        Member member = isPresentMember(requestDto.getUsername());
        if (null == member) {
            return ResponseDto.fail("사용자를 찾을 수 없습니다.");
        }

        if (!member.validatePassword(passwordEncoder, requestDto.getPassword())) {
            return ResponseDto.fail("사용자를 찾을 수 없습니다.");
        }

        TokenDto tokenDto = tokenProvider.generateTokenDto(member);
        tokenToHeaders(tokenDto, response);

        return ResponseDto.success(
                MemberResponseDto.builder()
                        .id(member.getId())
                        .username(member.getUsername())
                        .createdAt(member.getCreatedAt())
                        .modifiedAt(member.getModifiedAt())
                        .build()
        );
    }

    public ResponseDto<?> logout(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("RefreshToken"))) {
            return ResponseDto.fail("Token이 유효하지 않습니다.");
        }
        Member member = tokenProvider.getMemberFromAuthentication();
        if (null == member) {
            return ResponseDto.fail("사용자를 찾을 수 없습니다.");
        }

        return tokenProvider.deleteRefreshToken(member);
    }

    @Transactional(readOnly = true)
    public Member isPresentMember(String  username) {
        Optional<Member> optionalMember = memberRepository.findByUsername(username);
        return optionalMember.orElse(null);
    }

    // Header에 값을 넣어주기 위한 메서드
    public void tokenToHeaders(TokenDto tokenDto, HttpServletResponse response) {
        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        response.addHeader("RefreshToken", tokenDto.getRefreshToken());
        response.addHeader("AccessTokenExpireTime", tokenDto.getAccessTokenExpiresIn().toString());
    }

}
