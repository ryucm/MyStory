package com.mystory.repository;

import com.mystory.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    public Member findByUserId(String userId);
}
