package com.mystory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberCreateDto {
    private String userId;
    private String userName;
    private String userEmail;
    private String password;
}

