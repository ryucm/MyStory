package com.mystory.controller;

import com.mystory.domain.Member;
import com.mystory.dto.MemberCreateDto;
import com.mystory.dto.SignupRequestDto;
import com.mystory.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/{id}")
    public Member user(@PathVariable("id") Long id) {
        return userService.findUser(id);
    }

    //Member 회원가입 페이지
    @GetMapping("/sign-up")
    public String signUp() {
        return "signup";
    }

    //Member 회원가입
    @PostMapping("sign-up")
    public String signUp(MemberCreateDto userCreateDto, Model model) {
        userService.signUp(userCreateDto);
        model.addAttribute("message", "Registered Successfully!");
        model.addAttribute("searchUrl", "/");
        return "message";
    }

    // Member 로그인 페이지
    @GetMapping("login")
    public String logIn() {
        return "login";
    }

    // 관리자 로그인 페이지
    @GetMapping("/user/login")
    public String login() {
        return "managerLogin";
    }

    // 관리자 회원 가입 페이지
    @GetMapping("/user/signup")
    public String signup() {
        return "managerSignUp";
    }

    @PostMapping("/user/signup")
    public String registerUser(SignupRequestDto requestDto) {
        userService.registerUser(requestDto);
        return "redirect:/user/login";
    }
}
