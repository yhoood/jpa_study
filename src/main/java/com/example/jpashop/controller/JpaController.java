package com.example.jpashop.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Slf4j
@RestController
@RequiredArgsConstructor
public class JpaController {

    @PersistenceContext
    private final EntityManager em;

    @Transactional
//    @GetMapping("/orders")
//    public void orders() {
//
//        log.info("orders controller");
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


//        List<Member> resultList =
//                em.createQuery("SELECT m FROM Order m join fetch m.member", Member.class)
//                .getResultList();
//
//        for(Member result : resultList){
//            System.out.println("result.toString() = " + result.toString());
//        }
        
//        Order orderData=em.find(Order.class,order.getOrderId());
//        System.out.println("정상테스트 실행:"+orderData.toString());
//    }

    @GetMapping("/noParam")
    public void noParam() {
        log.info("noParam controller");
    }
    @GetMapping("/getSave")
    public String getSave(@RequestParam String id) {
        log.info("getSave controller");
        System.out.println("id= " +id);
        return id;
    }

    @RequestMapping("/requestSave")
    public Map<String, Object> requestSave(HttpServletRequest request, @RequestParam Map<String, Object> paramMap) {
        log.info("requestSave controller");
        String param =request.getParameter("id");
        System.out.println("param.toString() = " + param.toString());
        System.out.println("paramMap.toString() = " + paramMap.toString());
        return paramMap;
    }

    @PostMapping("/postSave")
    public Map<String, Object> postSave(HttpServletRequest request, @RequestParam Map<String, Object> paramMap) {
        log.info("postSave controller");
        String param =request.getParameter("id");
        System.out.println("param.toString() = " + param.toString());
        System.out.println("paramMap.toString() = " + paramMap.toString());
        return paramMap;
    }

//    @PostMapping(value = "/members/new")
//    public String create(@Valid MemberForm form, BindingResult result) {
//        if (result.hasErrors()) {
//            return "members/createMemberForm";
//        }
//        Address address = new Address(form.getCity(), form.getStreet(),
//                form.getZipcode());
//        Member member = new Member();
//        member.setName(form.getName());
//        member.setAddress(address);
//        memberService.join(member);
//        return "redirect:/";
//    }
}
