package com.mystory.domain;

import com.mystory.dto.MemberCreateDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String userEmail;

    @Column(nullable = false)
    private String password;


    public Member(MemberCreateDto userCreateDto) {
        this.userId = userCreateDto.getUserId();
        this.username = userCreateDto.getUserName();
        this.userEmail = userCreateDto.getUserEmail();
        this.password = userCreateDto.getPassword();
    }

    public Member(String userId, String username, String password, String userEmail) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.userEmail = userEmail;
    }

}
