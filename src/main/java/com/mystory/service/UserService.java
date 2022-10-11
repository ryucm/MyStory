package com.mystory.service;


import com.mystory.domain.Member;
import com.mystory.domain.User;
import com.mystory.domain.UserRoleEnum;
import com.mystory.dto.MemberCreateDto;
import com.mystory.dto.MemberLoginDto;
import com.mystory.dto.SignupRequestDto;
import com.mystory.repository.MemberRepository;
import com.mystory.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";


    @Transactional
    public Member findUser(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Post ID does not exist"));
    }

    @Transactional
    public void signUp(MemberCreateDto userCreateDto) {
        Member user = new Member(userCreateDto);
        memberRepository.save(user);
    }

    @Transactional
    public void signIn(MemberLoginDto userLoginDto) {
        Member user = memberRepository.findByUserId(userLoginDto.getUserId());
    }

    public void registerUser(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
        }

        String password = passwordEncoder.encode(requestDto.getPassword());
        // 회원 ID 중복 확인

        String email = requestDto.getEmail();
        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!requestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(username, password, email, role);
        userRepository.save(user);
    }
}