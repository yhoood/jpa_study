package com.example.jpashop.controller;

import com.example.domain.jpashop.Address;
import com.example.domain.jpashop.Member;
import com.example.jpashop.dto.MemberDto;
import com.example.jpashop.parameter.MemberSearchParameter;
import com.example.jpashop.repository.MemberRepository;
import com.example.jpashop.repository.MemberSupportRepository;
import com.example.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    private final MemberRepository memberRepository;
    private final MemberSupportRepository memberSupportRepository;

    @PostMapping("/signIn")
    public Map<String, Object> signIn(@RequestParam Map<String, Object> paramMap) {
        log.info("signIn controller");
        System.out.println("paramMap.toString() = " + paramMap.toString());

        Address address = new Address(paramMap.get("city").toString() ,paramMap.get("street").toString() ,paramMap.get("zipcode").toString());
        Member member = new Member(paramMap.get("memberName").toString(),address);
        memberService.save(member);

        return paramMap;
    }

    @GetMapping("/memberList")
    public List<Member> memberList(@RequestParam Map<String, Object> paramMap) {
        log.info("memberList controller");
        //return memberService.findAllnoInterface();

        return memberService.findAll();                                                   //JpaRepository 기본 메소드 (all)
        //return memberService.findByMemberId(1);                                           //method name query (pk)
        //return memberService.findByMemberIdAndMemberName(2,"김아무개");                    //method name query (optional)
        //return memberService.findByMemberIdInOrAddressCity(Arrays.asList(8L,9L),"서울");   //method name query (in)
        //return memberService.findByMemberParam(Arrays.asList(8L,9L),"서울");          //@Query Annotation (in)
        //return memberService.findAllMemberDto();                                          //@Query Dto사용
        //return memberService.findLeftFetchSelect();                                       //left join fetch
    }

    @GetMapping("/membersPage")
    public Page<MemberDto> memberList2(Pageable pageable) {
        log.info("memberList controller");
        Page<Member> page = memberService.findMemberPage(pageable);
        Page<MemberDto> result = page.map(member ->new MemberDto(member)); //변환전
        //Page<MemberDto> result = page.map(MemberDto::new);
        return result;
    }

    @GetMapping("/call")
    public List<MemberDto> call(MemberSearchParameter param) {
        log.info("call controller");
        return memberRepository.searchWhereParam(param);
    }

    @GetMapping("/callPage")
    public Page<MemberDto> callPage(MemberSearchParameter param, Pageable pageable) {
        log.info("callPage controller");
        return memberRepository.searchPageComplex(param,pageable);
    }

    @GetMapping("/callPages")
    public Page<MemberDto> callPages(MemberSearchParameter param, Pageable pageable) {
        log.info("callPages controller");
        Page<Member> page =memberSupportRepository.searchPagination(param,pageable);
        return  page.map(MemberDto::new);
    }
}
