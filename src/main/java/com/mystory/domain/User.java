package com.mystory.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true)
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z]).{4,12}", message = "닉네임은 최소 4자 이상, 12자 이하 알파벳 대소문자(a-z, A-Z), 숫자(0-9)로 구성됩니다.")
    private String username;

    @Column(nullable = false)
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z]).{4,32}", message = "비밀번호는 최소 4자 이상, 32자 이하 알파벳 소문자(a-z), 숫자(0-9)로 구성됩니다.")
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(String username, String password, String email, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}
