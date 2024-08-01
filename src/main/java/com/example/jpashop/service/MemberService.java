package com.example.jpashop.service;

//import com.example.jpashop.repository.MemberJpaRepository;
import com.example.domain.jpashop.Member;
import com.example.jpashop.dto.MemberDto;
import com.example.jpashop.repository.MemberJpaRepository;
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
    private final MemberJpaRepository memberJpaRepository;

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

//    public List<Member> findAllnoInterface() {
//        return memberJpaRepository.findAll();
//    }

    //JpaRepository 기본 메소드 (all)
    public List<Member> findAll() {
        return memberRepository.findAll();
    }
    //JpaRepository 기본 메소드 (pk)
    public Optional<Member> findById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    //namedQuery
    public List<Member> findByMemberName(String memberName) {
        return memberRepository.findByMemberName(memberName);
    }

    //method name query (pk)
    public Member findByMemberId(long id) {
        return memberRepository.findByMemberId(id);
    }

    //method name query (optional)
    public Member findByMemberIdAndMemberName(long id, String name) {
        return memberRepository.findByMemberIdAndMemberName(id, name).orElse(Member.createMember("이든"));
    }

    //method name query (in)
    public List<Member> findByMemberIdInOrAddressCity(List<Long> idList, String city){
        return memberRepository.findByMemberIdInOrAddressCity(idList, city);
    }

    //@Query Annotation (in)
    public List<Member> findByMemberParam(List<Long> idList, String city){
        return memberRepository.findByMemberParam(idList, city);
    }
    //@Query Dto사용
    public List<MemberDto> findAllMemberDto() {
        return memberRepository.findAllMemberDto();
    }

    //join fetch
    public List<Member> findFetchSelect() {
        return memberRepository.findFetchSelect();
    }
    //left join fetch
    public List<Member> findLeftFetchSelect() {
        return memberRepository.findLeftFetchSelect();
    }
    //24.07.29_인터페이스 리파짓토리로 바꾸고 주석
//    public Member findOne(Long memberId) {
//        return memberRepository.findOne(memberId);
//    }

}
