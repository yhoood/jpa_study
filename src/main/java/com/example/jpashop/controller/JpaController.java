package com.example.jpashop.controller;

import com.example.domain.jpashop.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
public class JpaController {

    @PersistenceContext
    private final EntityManager em;

    @Transactional
    @GetMapping("/orders")
    public void orders() {
        //Delivery delivery = new Delivery("order_01","김포시","김포한강8로 103동 803호","10067",);
        //em.persist(delivery);

//        Member member = new Member();
//        member.setMemberName("yhood");
//        member.setCity("걸포북변역");
//        em.persist(member);
//
//        Delivery delivery = new Delivery();
//        delivery.setCity("고촌역");

//        em.persist(delivery);
//
//        Order order = new Order();
//        order.setMember(member);
//        order.setDelivery(delivery);
//        LocalDateTime localDateTime = LocalDateTime.now();
//        order.setOrderDate(localDateTime);
//        order.setStatus(ORDER);
//        em.persist(order);


        List<Member> resultList =
                em.createQuery("SELECT m FROM Order m join fetch m.member", Member.class)
                .getResultList();
        
        for(Member result : resultList){
            System.out.println("result.toString() = " + result.toString());
        }
        
//        Order orderData=em.find(Order.class,order.getOrderId());
//        System.out.println("정상테스트 실행:"+orderData.toString());
    }
}
