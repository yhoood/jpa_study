package com.example.controller;

import com.example.entity.Delivery;
import com.example.entity.Member;
import com.example.entity.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;

import static com.example.entity.EnumOrderStatus.ORDER;

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

        Member member = new Member();
        member.setMemberName("김아무개");
        member.setCity("서울을지로");
        em.persist(member);

        Delivery delivery = new Delivery();
        delivery.setCity("시청역맛집");
        em.persist(delivery);

        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        LocalDateTime localDateTime = LocalDateTime.now();
        order.setOrderDate(localDateTime);
        order.setStatus(ORDER);


        em.persist(order);
        Order orderData=em.find(Order.class,order.getOrderId());
        System.out.println("이것은 지금 빌드가 되었습니다:"+orderData.toString());

    }
}
