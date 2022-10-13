package com.mystory.controller;

import com.mystory.domain.User;
import com.mystory.dto.user.LoginRequestDto;
import com.mystory.dto.ResponseDto;
import com.mystory.dto.user.SignupRequestDto;
import com.mystory.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@RestController
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
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("managerLogin");
        return modelAndView;
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> login(LoginRequestDto requestDto, HttpServletResponse httpServletResponse) {
        return userService.login(requestDto, httpServletResponse);
    }

    // 회원 가입 페이지
    @GetMapping("/user/sign-up")
    public ModelAndView signup() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("managerSignUp");
        return modelAndView;
    }

    @PostMapping("/user/sign-up")
    public ResponseDto<?> registerUser(SignupRequestDto requestDto) {
        return userService.registerUser(requestDto);
    }

}
