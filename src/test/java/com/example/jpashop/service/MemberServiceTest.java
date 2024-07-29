package com.example.jpashop.service;
import com.example.domain.jpashop.Member;
import com.example.jpashop.repository.MemberJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
//@Rollback(value = false)
public class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberJpaRepository memberJpaRepository;
    @Test
    public void 회원가입() throws Exception {
        //Given
        Member member = new Member();
        member.setMemberName("kim");
        //When
        Long saveId = memberService.save(member);
        //Then
        assertEquals(member, memberJpaRepository.findOne(saveId));
    }
    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //Given
        Member member1 = Member.createMember();
        member1.setMemberName("kim");
        Member member2 = Member.createMember();
        member2.setMemberName("kim");
        //When
        memberService.save(member1);
        memberService.save(member2); //예외가 발생해야 한다.
        //Then
        fail("예외가 발생해야 한다.");
    }
}