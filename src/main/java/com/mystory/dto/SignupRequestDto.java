package com.mystory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupRequestDto {
    private String username;
    private String password;
    private String email;
    private boolean admin = false;
    private String adminToken = "";
}
