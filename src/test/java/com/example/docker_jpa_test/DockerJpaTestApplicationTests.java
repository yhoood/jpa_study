package com.example.docker_jpa_test;

import com.example.entity.Delivery;
import com.example.entity.Member;
import com.example.entity.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static com.example.entity.EnumOrderStatus.*;

@SpringBootTest
class DockerJpaTestApplicationTests {

	@Autowired private EntityManager em;

	@Test
	@Transactional
	@Rollback(false)
	void item() {
		System.out.println("===test start===");
//		ItemDrink drink = new ItemDrink("cokacola",500);
//		em.persist(drink);

		Member member = new Member();
		member.setMemberName("ddd");
		member.setCity("영등포");
		em.persist(member);

		Delivery delivery = new Delivery();
		delivery.setCity("서울역");
		em.persist(delivery);

		Order order = new Order();
		order.setMember(member);
		order.setDelivery(delivery);
		LocalDateTime localDateTime = LocalDateTime.now();
		order.setOrderDate(localDateTime);
		order.setStatus(ORDER);
		em.persist(order);


		/*****1.TypeQuery*****/
		System.out.println("*****1.TypeQuery*****");

		//TypeQuery : 반환할 타입을 명확하게 지정할 수 있을 경우
		TypedQuery<Member> query1 =
				em.createQuery("SELECT m FROM Member m", Member.class);

		List<Member> resultList1 = query1.getResultList();

		for (Member result : resultList1) {
			System.out.println("1. ->member = " + result.toString());
		}

		/*****2.Query*****/
		System.out.println("*****2.Query*****");

		//Query : 반환 타입을 명확하게 지정할 수 없을 경우,여러 엔티티나 컬럼을 선택할 경우(반환 타입이 명확하지 않을 경우) 사용
		Query query2 =
				em.createQuery("SELECT m.memberId,m.memberName FROM Member m");

		List resultList2 = query2.getResultList();
		for (Object object : resultList2) {
			Object[] result = (Object[]) object; // 결과가 둘 이상일 경우 Object[]
			System.out.println("2 -> memberId = " + result[0]);
			System.out.println("2 -> memberName = " + result[1]);
		}

		/*****3.Parameter*****/
		System.out.println("*****3.Parameter*****");

		String memberNameParam ="yhood";
		
		TypedQuery<Member> query3 =										//:memberName 파라미터 지정
				em.createQuery("SELECT m FROM Member m WHERE m.memberName=:memberName", Member.class);
		//파라미터 바인딩
		query3.setParameter("memberName",memberNameParam);

		List<Member> resultList3 = query3.getResultList();

		for (Member result : resultList3) {
			System.out.println("3 -> member = " + result.toString());
		}

		/*****4.메소드체인*****/
		System.out.println("*****4.메소드체인*****");

		String memberNameParam2 ="kkk";

		List<Member> resultList4 =
				em.createQuery("SELECT m FROM Member m WHERE memberName=:memberName",Member.class)
				.setParameter("memberName",memberNameParam2)
				.getResultList();

		for (Member result : resultList4) {
			System.out.println("4 -> member = " + result.toString());
		}


		//**************jpql 기본조회
//		String jpql = "select m from Member as m where m.memberName = 'yhood'";
//		List<Member> resultList = em.createQuery(jpql, Member.class).getResultList();
//		for(Member result:resultList){
//			System.out.println("1111111111result= "+result.toString());
//		}


		//**************em.find
		//Order orderData=em.find(Order.class,order.getOrderId());
		//System.out.println(orderData.toString());


	}

}
