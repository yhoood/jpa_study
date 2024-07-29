package com.example.jpashop.repository;

import com.example.domain.jpashop.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
