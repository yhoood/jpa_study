package com.example.jpashop.service;

//import com.example.jpashop.repository.MemberJpaRepository;
import com.example.domain.jpashop.Member;
import com.example.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     */
    @Transactional //변경
    public Long save(Member member) {
        //24.07.29_인터페이스 리파짓토리로 바꾸고 주석
        //validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getMemberId();
    }
    //24.07.29_인터페이스 리파짓토리로 바꾸고 주석
//    private void validateDuplicateMember(Member member) {
//        List<Member> findMembers =
//                memberRepository.findByName(member.getMemberName());
//        if (!findMembers.isEmpty()) {
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        }
//    }
    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
    public Optional<Member> findById(Long memberId) {
        return memberRepository.findById(memberId);
    }
    //24.07.29_인터페이스 리파짓토리로 바꾸고 주석
//    public Member findOne(Long memberId) {
//        return memberRepository.findOne(memberId);
//    }

}
