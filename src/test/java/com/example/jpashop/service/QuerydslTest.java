package com.example.jpashop.service;

import com.example.domain.jpashop.Member;
import com.example.jpashop.dto.MemberDto;
import com.example.jpashop.dto.QMemberDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.domain.jpashop.QMember.member;

@SpringBootTest
@Transactional(readOnly = true)
public class QuerydslTest {
    @PersistenceContext
    EntityManager em;

    //쿼리팩토리 필드선언
    JPAQueryFactory queryFactory;

    //테스트 시작전 초기 실행
    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);
    }

    /*
    프로젝션 : 컬럼 개별 호출
     */
    @Test
    public void projectionTest() throws Exception {
        List<String> result = queryFactory
                .select(member.memberName)
                .from(member)
                .fetch();
        for(String s : result){
            System.out.println("s = " + s);
        }
        /*
        * Hibernate:
            select
                m1_0.member_name
            from
                TB_MEMBER m1_0
        s = 고구마
        s = 감자
        s = 손흥민
        s = 오홍홍
        s = 홍길동
        s = 국밥
        s = 김사과
        s = 김모씨
        s = 장발장
        s = 고드름
        s = 김서방
        s = 달님
        s = 태양
        * */
    }

    /*
    프로젝션 : Tuple로 호출 가능
     */
    @Test
    public void tupleTest() throws Exception {
        List<Tuple> result = queryFactory
                .select(member.memberName, member.memberId)
                .from(member)
                .fetch();
        for (Tuple tuple : result) {
            String memberName = tuple.get(member.memberName);
            Long memberId = tuple.get(member.memberId);
            System.out.println("memberName=" + memberName);
            System.out.println("memberId=" + memberId);
        }

        /*
        Hibernate:
        select
            m1_0.member_name,
            m1_0.member_id
        from
            TB_MEMBER m1_0

        *memberName=고구마
        memberId=1
        memberName=감자
        memberId=2
        memberName=손흥민
        memberId=3
        memberName=오홍홍
        memberId=4
        memberName=홍길동
        memberId=5
        memberName=국밥
        memberId=6
        memberName=김사과
        memberId=7
        memberName=김모씨
        memberId=8
        memberName=장발장
        memberId=9
        memberName=고드름
        memberId=10
        memberName=김서방
        memberId=11
        memberName=달님
        memberId=12
        memberName=태양
        memberId=13
         */
    }

    /*
    Projections.bean() 세터주입
    Projections.fields() 필드주입
    Projections.constructor() 생성자주입

    Tip -> Dto의 변수명이 다르면 member.meberName.as("dtoMemberName")
     */
    @Test
    public void projectionsBeanTest() throws Exception {
        //세터주입
        List<MemberDto> result = queryFactory
                .select(Projections.bean(MemberDto.class
                        ,member.memberName
                        ,member.address.city
                        ,member.address.street
                        ,member.address.zipcode))
                .from(member)
                .fetch();
        //필드주입
        List<MemberDto> result2 = queryFactory
                .select(Projections.fields(MemberDto.class
                        ,member.memberName
                        ,member.address.city
                        ,member.address.street
                        ,member.address.zipcode))
                .from(member)
                .fetch();
        //생성자주입
        List<MemberDto> result3 = queryFactory
                .select(Projections.constructor(MemberDto.class
                        ,member.memberName
                        ,member.address.city
                        ,member.address.street
                        ,member.address.zipcode))
                .from(member)
                .fetch();
    }

    /*
    @QueryProjection
    :Dto 생성자에 선언후 java컴파일해서 QMemeberDto 생성 확인 필요
    Dto가 QueryDsl에 종속적이기에 고민해야함 (QMemberDto 필수)
     */
    @Test
    public void queryProjectionTest() throws Exception {
        List<MemberDto> result = queryFactory
                .select(new QMemberDto(
                         member.address.city
                        ,member.address.street
                        ,member.address.zipcode))
                .from(member)
                .fetch();
    }

    /*
    *동적쿼리 BooleanBuilder
     */
    @Test
    public void booleanBuilderTest() throws Exception {
        String nameParam = null;//"감자";
        String cityParam = "강원도";
        searchMember1(nameParam, cityParam).stream().forEach(System.out::println);

    }

    private List<MemberDto> searchMember1(String name, String city) {
        BooleanBuilder Bbuilder = new BooleanBuilder();
        if (name != null) {
            Bbuilder.and(member.memberName.eq(name));
        }
        if (city != null) {
            Bbuilder.and(member.address.city.eq(city));
        }
        return queryFactory
                .select(Projections.constructor(MemberDto.class
                        ,member.memberName
                        ,member.address.city
                        ,member.address.street
                        ,member.address.zipcode))
                .from(member)
                .where(Bbuilder)
                .fetch();
    }


    /*
    동적쿼리 : whereParam
     */
    @Test
    public void whereParamTest() throws Exception {
        String nameParam = null;//"감자";
        String cityParam = "강원도";
        searchMember2(nameParam, cityParam).stream().forEach(System.out::println);
    }
    private List<MemberDto> searchMember2(String name, String city) {
        return queryFactory
                .select(Projections.constructor(MemberDto.class
                        ,member.memberName
                        ,member.address.city
                        ,member.address.street
                        ,member.address.zipcode))
                .from(member)
                .where(nameEq(name), cityEq(city))
                .fetch();
    }
    //반환타입 BooleanExpression
    private BooleanExpression nameEq(String name) {
        return name != null ? member.memberName.eq(name) : null;
    }
    //반환타입 BooleanExpression
    private BooleanExpression cityEq(String city) {
        return city != null ? member.address.city.eq(city) : null;
    }

    /*
    벌크연산, 반드시 em.flush또는 @modifiying필요
    */
    @Test
    public void bulkTest() throws Exception {
        long count = queryFactory
                .update(member)
                .set(member.memberName, "비회원")
                .where(member.memberId.eq(9L))
                .execute();

        long count2 = queryFactory
                .delete(member)
                .where(member.memberId.eq(10L))
                .execute();
    }

    /*
    SQL function 호출
    공통펑션을 제외하고는 Dialect에 등록해야함
     */
    @Test
    public void sqlFuncTest() throws Exception {
        List<String> resultList1 = queryFactory
                .select(Expressions.stringTemplate("function('replace', {0}, {1}, {2})",
                        member.memberName, "김", "M"))
                .from(member)
                .fetch();

        for(String str : resultList1){
            System.out.println("str = " + str);
        }

        List<String> resultList2 = queryFactory
                .select(member.memberName)
                .from(member)
//                .where(member.memberName.eq(Expressions.stringTemplate("function('lower', {0})",
//                        member.memberName)))
                //안시 표준함수는 그냥 싫행하면 됨.
                .where(member.memberName.eq(member.memberName.lower())).fetch();

        for(String str2 : resultList2){
            System.out.println("str2 = " + str2);
        }


    }

}
