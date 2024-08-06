package com.example.jpashop.service;

import com.example.domain.jpashop.Member;
import com.example.domain.jpashop.QMember;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.domain.jpashop.QMember.*; //QMmeber 스태틱 선언
import static org.assertj.core.api.Assertions.assertThat;

//@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional(readOnly = true)
public class QuerydslTest {

    @PersistenceContext
    EntityManager em;

    JPAQueryFactory queryFactory;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);
    }


    @Test
    public void queryDslUseTest() throws Exception {
        //QMember qMember = new QMember("m"); //별칭 직접 지정
        //QMember qm = QMember.member; //기본 인스턴스 사용
        //QMember 스태틱 선언 으로 member로 사용
        Member findMember = queryFactory
                .select(member)
                .from(member)
                .where(member.memberName.eq("김사과"))
                .fetchOne();

        assertThat(findMember.getAddress().getCity()).isEqualTo("인천");
    }
    @Test
    public void queryDslUseTest2() throws Exception {
        List<Member> memberList = queryFactory.selectFrom(member).fetch();

        long count = memberList.stream()
                .filter(member ->member.getAddress().getCity().equals("천안")).count();
//                .forEach(System.out::println);
        System.out.println("스트림 카운트 :"+count);

        QueryResults result = queryFactory.selectFrom(member).fetchResults();
        System.out.println("전체카운트 :" + result.getTotal());
    }

    @Test
    public void queryDslUseTest3() throws Exception {
        List<Member> memberList = queryFactory
                .selectFrom(member)
                .where(
                        member.address.city.eq("천안"),
                        member.memberName.eq("달님")
                )
                .fetch();
    }
}
