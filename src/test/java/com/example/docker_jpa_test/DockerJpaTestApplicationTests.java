package com.example.docker_jpa_test;

import com.example.entity.Delivery;
import com.example.entity.Member;
import com.example.entity.Order;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.Date;

import static com.example.entity.EnumOrderStatus.*;

@SpringBootTest
class DockerJpaTestApplicationTests {

	@Autowired private EntityManager em;

//	@Test
//	@Transactional
//	@Rollback(false)
//	void item() {
//		System.out.println("test");
////		ItemDrink drink = new ItemDrink("cokacola",500);
////		em.persist(drink);
//
//		Member member = new Member();
//		member.setMemberName("양아무개");
//		member.setCity("레이크에일린의뜰");
//		em.persist(member);
//
//		Delivery delivery = new Delivery();
//		delivery.setCity("장기동배달지");
//		em.persist(delivery);
//
//		Order order = new Order();
//		order.setMember(member);
//		order.setDelivery(delivery);
//		Date now = new Date();
//		order.setOrderDate(now);
//		order.setStatus(ORDER);
//
//
//		em.persist(order);
//		Order orderData=em.find(Order.class,order.getOrderId());
//		System.out.println(orderData.toString());
//
//
//	}

}
