package com.mystory.controller;

import com.mystory.domain.User;
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
    public User user(@PathVariable("id") Long id) {
        return userService.findUser(id);
    }

    //Member 회원가입 페이지

    // 로그인 페이지
    @GetMapping("/user/login")
    public String login() {
        return "managerLogin";
    }

    // 회원 가입 페이지
    @GetMapping("/user/sign-up")
    public String signup() {
        return "managerSignUp";
    }

    @PostMapping("/user/sign-up")
    public String registerUser(SignupRequestDto requestDto) {
        System.out.println(requestDto);
        userService.registerUser(requestDto);
        return "redirect:/";
    }
}
