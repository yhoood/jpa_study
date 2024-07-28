package com.example.jpashop.controller;

import com.example.domain.jpashop.Address;
import com.example.domain.jpashop.Member;
import com.example.jpashop.repository.MemberRepository;
import com.example.jpashop.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {

    private final MemberService memberService;

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
        System.out.println("paramMap.toString() = " + paramMap.toString());
        return memberService.findMembers();
    }
}
