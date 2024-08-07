package com.example.jpashop.service;
import com.example.domain.jpashop.*;
import com.example.jpashop.dto.MemberDto;
import com.example.jpashop.repository.MemberJpaRepository;
import com.example.jpashop.repository.MemberRepository;
import com.example.jpashop.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


@SpringBootTest
@Transactional(readOnly = true)
public class MemberServiceTest {
    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;

    @Autowired
    MemberService memberService;
//    @Autowired
//    OrderRepository orderRepository;
    @Test
    @Transactional
    @Rollback(false)
    public void springDataJpaTest() throws Exception {
        PageRequest pageRequest = PageRequest.of(0, 2,Sort.by(Sort.Direction.DESC,"memberName"));
        Page<Member> page = memberService.findMemberPage(pageRequest);
        System.out.println(page.getContent());
        System.out.println(page.getContent().size());
        System.out.println(page.getTotalElements());
        System.out.println(page.getNumber());
        System.out.println(page.getTotalPages());
        System.out.println(page.isFirst());
        System.out.println(page.hasNext());
    }


//    @PersistenceContext
//    EntityManager em;
//    @Autowired
//    MemberJpaRepository memberJpaRepository;


//    @Test
//    public void 회원가입() throws Exception {
//        //Given
//        Member member = Member.createMember();
//        member.setMemberName("kim");
//        //When
//        Long saveId = memberService.save(member);
//        //Then
//        assertEquals(member, memberJpaRepository.findOne(saveId));
//    }
//    @Test(expected = IllegalStateException.class)
//    public void 중복_회원_예외() throws Exception {
//        //Given
//        Member member1 = Member.createMember();
//        member1.setMemberName("kim");
//        Member member2 = Member.createMember();
//        member2.setMemberName("kim");
//        //When
//        memberService.save(member1);
//        memberService.save(member2); //예외가 발생해야 한다.
//        //Then
//        fail("예외가 발생해야 한다.");
//    }

    @Test
    //@Rollback(false)
    public void member_select_test() throws Exception {
        //Given
//        Member member1 = Member.createMember();
//        member1.setMemberName("kim");
//        Member member2 = Member.createMember();
//        member2.setMemberName("yang");
//
//        memberService.save(member1);
//        memberService.save(member2);

//        //member1
//        Order order11 = new Order();
//        Order order12 = new Order();
//        order11.injectMember(member1);
//        order12.injectMember(member1);
//
//        //member2
//        Order order21 = new Order();
//        Order order22 = new Order();
//        Order order23 = new Order();
//        Order order24 = new Order();
//        order21.injectMember(member2);
//        order22.injectMember(member2);
//        order23.injectMember(member2);
//        order24.injectMember(member2);
//
//        orderRepository.save(order11);
//        orderRepository.save(order12);
//        orderRepository.save(order21);
//        orderRepository.save(order22);
//        orderRepository.save(order23);
//        orderRepository.save(order24);



        //When
        //List<Member> members = memberService.findAllnoInterface();

        //List<Member> members = memberService.findByMemberId(1);
        //Member member =memberService.findByMemberIdAndMemberName(3,"yhoood");
//        System.out.println("member.toString() = " + member.getMemberName());

        //List<Order> orders = orderRepository.findAll();


        //List<Member> members = memberService.findByMemberName("yhoood");

        //List<Member> members = memberService.findByMemberIdInOrAddressCity(Arrays.asList(8L,9L),"서울");
        //List<Member> members = memberService.findByMemberParam(Arrays.asList(8L,9L),"서울");
//        System.out.println("members.size() = " + members.size());
       //dto test
//        List<MemberDto> memberDtoList =  memberService.findAllMemberDto();
//        Iterator<Member> iterator = memberList.iterator();
//        while (iterator.hasNext()) {
//            Member member = iterator.next();
//            System.out.println("memberDto.toString() = " + member.getMemberName()+"/"+member.getCity());
//        }

        //지연로딩 테스트
//        em.clear();
//        Order order = em.find(Order.class,order11.getOrderId());
//        System.out.println("order.toString() = " + order.toString());
//        System.out.println(" ===================== ");
//        System.out.println(order.getMember());


        //Then
//        Iterator<Member> iterator = members.iterator();
//        while (iterator.hasNext()) {
//            Member member = iterator.next();
//            System.out.println("member.toString() = " + member.getMemberId()+"/"+member.getMemberName());
//        }

//        Iterator<Order> iterator = orders.iterator();
//        while (iterator.hasNext()) {
//            Order order = iterator.next();
//            System.out.println("order.toString() = " + order.toString());
//        }
    }
}