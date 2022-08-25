package com.example.simpletwiter_be.controller;

import com.example.simpletwiter_be.dto.request.LoginRequestDto;
import com.example.simpletwiter_be.dto.request.MemberRequestDto;
import com.example.simpletwiter_be.dto.response.ResponseDto;
import com.example.simpletwiter_be.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
public class MemberController {
    private final MemberService memberService;

    @Autowired
    MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @RequestMapping(value = "/api/user/signup", method = RequestMethod.POST)
    public ResponseDto<?> signup(@ModelAttribute MemberRequestDto requestDto) throws Exception {
        return memberService.createMember(requestDto);
    }

    @RequestMapping(value = "/api/user/login", method = RequestMethod.POST)
    public ResponseDto<?> login(@RequestBody @Valid LoginRequestDto requestDto,
                                HttpServletResponse response)
    {
        System.out.println(requestDto.getUsername());
        return memberService.login(requestDto, response);
    }

    @RequestMapping(value = "/api/logout", method = RequestMethod.POST)
    public ResponseDto<?> logout(HttpServletRequest request) {
        return memberService.logout(request);
    }
}
