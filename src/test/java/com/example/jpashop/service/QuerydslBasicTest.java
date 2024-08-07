package com.example.jpashop.service;

import com.example.domain.jpashop.Member;
import com.example.domain.jpashop.QMember;
import com.example.domain.jpashop.QOrder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.domain.jpashop.QMember.*; //QMmeber 스태틱 선언
import static org.assertj.core.api.Assertions.assertThat;

//@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional(readOnly = true)
public class QuerydslBasicTest {

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
    QueryDsl시작
     */
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
        //stream사용해서 바로 출력
        queryFactory.selectFrom(member).fetch().stream().forEach(System.out::println);

//        List<Member> memberList = queryFactory.selectFrom(member).fetch();
//        memberList.stream().forEach(System.out::println);


//        long count = memberList.stream()
//                .filter(member ->member.getAddress().getCity().equals("천안")).count();
//        System.out.println("스트림 카운트 :"+count);
//
//        QueryResults result = queryFactory.selectFrom(member).fetchResults();
//        System.out.println("전체카운트 :" + result.getTotal());
    }

    //.where and조건
    @Test
    public void search() {
        Member findMember = queryFactory
                .selectFrom(member)
                .where(member.memberName.eq("국밥")
                        .and(member.address.city.eq("부산")))
                .fetchOne();
        assertThat(findMember.getMemberName()).isEqualTo("국밥");
    }

    /*
    [where 조건들]

    member.memberName.eq("member1") // username = 'member1'
    member.memberName.ne("member1") //username != 'member1'
    member.memberName.eq("member1").not() // username != 'member1'
    member.memberName.isNotNull() //이름이 is not null

    member.age.in(10, 20) // age in (10,20)
    member.age.notIn(10, 20) // age not in (10, 20)
    member.age.between(10,30) //between 10, 30

    member.age.goe(30) // age >= 30
    member.age.gt(30) // age > 30
    member.age.loe(30) // age <= 30
    member.age.lt(30) // age < 30

    member.username.like("member%") //like 검색
    member.username.contains("member") // like ‘%member%’ 검색
    member.username.startsWith("member") //like ‘member%’ 검색

     */

    //where을 다르게 표현
    @Test
    public void searchAndParam() throws Exception {
        List<Member> memberList = queryFactory
                .selectFrom(member)
                .where(
                        member.address.city.eq("천안"),
                        member.memberName.eq("달님")
                )
                .fetch();
        System.out.println("memberList.toString() = " + memberList.toString());
    }

    /*
    결과조회 방식
    fetch() : 리스트 조회, 데이터 없으면 빈 리스트 반환
    fetchOne() : 단 건 조회
    결과가 없으면 : null
    결과가 둘 이상이면 : com.querydsl.core.NonUniqueResultException
    fetchFirst() : limit(1).fetchOne()
    fetchResults() : 페이징 정보 포함, total count 쿼리 추가 실행
    fetchCount() : count 쿼리로 변경해서 count 수 조회

    //List
    List<Member> fetch = queryFactory.selectFrom(member).fetch();
    //단 건
    Member findMember1 = queryFactory.selectFrom(member).fetchOne();
    //처음 한 건 조회
    Member findMember2 = queryFactory.selectFrom(member).fetchFirst();
    //페이징에서 사용
    QueryResults<Member> results = queryFactory.selectFrom(member).fetchResults();
    //count 쿼리로 변경
    long count = queryFactory.selectFrom(member).fetchCount();
     */

    /**
     * 회원 정렬 순서
     * 1. 회원 id 내림차순(desc)
     * 2. 회원 이름 올림차순(asc)
     * 단 2에서 회원 이름이 없으면 마지막에 출력(nulls last)
     */
    @Test
    public void sort() {
        List<Member> result = queryFactory
                .selectFrom(member)
                .orderBy(member.memberId.desc(), member.memberName.asc().nullsLast()) //null은 마지막으로
                .fetch();

        for(int i=0; i<result.size();i++){
            System.out.println("id:"+result.get(i).getMemberId()+"/name:"+result.get(i).getMemberName());
        }
    }

    //offset, limit
    @Test
    public void paging1() {
        List<Member> result = queryFactory
                .selectFrom(member)
                .orderBy(member.memberName.desc())
                .offset(1) //0부터 시작(zero index)
                .limit(2) //최대 2건 조회
                .fetch();
        assertThat(result.size()).isEqualTo(2);
    }

    /*
    QueryResults 타입 조회
    getTotal값이 나오지만, SpringDataJPA와 마찬가지로, count는 원치않는 조인같은것 등으로
    성능 저하가 일어날 수 있기에 따로 작성 할 필요성이 있음
     */
    @Test
    public void paging2() {
        QueryResults<Member> queryResults = queryFactory
                .selectFrom(member)
                .orderBy(member.memberName.desc())
                .offset(1)
                .limit(2)
                .fetchResults();
        assertThat(queryResults.getTotal()).isEqualTo(4);
        assertThat(queryResults.getLimit()).isEqualTo(2);
        assertThat(queryResults.getOffset()).isEqualTo(1);
        assertThat(queryResults.getResults().size()).isEqualTo(2);
    }

    /**
    집합함수
     */
    @Test
    public void aggregation() throws Exception {
        queryFactory
                .select(member.count(),
                        member.memberId.sum(),
                        member.memberId.avg(),
                        member.memberId.max(),
                        member.memberId.min())
                .from(member)
                .fetch().stream().forEach(System.out::println);
                /*
                [13, 91, 7.0, 13, 1]
                 */
    }

    /**
     * leftJoin
     * groupBy
     * having
     *
     * [조인]
     * join(조인 대상, 별칭으로 사용할 Q타입)
     * orders의 where조건을 주기위해서 필요
     * .from(member)
     * .leftJoin(member.orderList, orders)
     * 
     * [조인조건 추가]
     * .from(member)
     * .leftJoin(member.orderList, orders)
     * .on(orders.orderId.eq(1L))
     */
    
    /*
    * 아무관계없는 엔티티 A,B join 할 경우
    * <일반조인>
    * .from(A)
    * .leftJoin(A).on(A.id.eq(B.id))
    * 
    * <세타조인> 
    * .from(A,B)
    *  where(A.id.eq(B.id)
    * 
    * :관계를 지정한 엔티티와 다르게 서로 key가 뭔지 모르기 때문에, 
    *  일반조인은 on절로 조건을 줘야 join이 되고
    *  세타조인은 where절로 조건을 줘야 join이 됨
    * */

    /*
    관계있는 조인과 관계없는 조인 차이
    관계조인: leftJoin(member.team, team)
    무관계 on조인: from(member).leftJoin(team).on(xxx)
     */
    @Test
    public void joinAndgroup() throws Exception {
        QOrder orders=QOrder.order;
                queryFactory
                .select(member.address.city
                        ,member.count())
                .from(member)
                .leftJoin(member.orderList,orders)//단순조인은 orders가 없어도 되나, orders의 값에 조건을 걸려면 2번째 인자가 필요함
                .on(orders.orderId.eq(1L))       //조인조건에 AND조건 추가
                //.where(orders.orderId.eq(1L))
                .groupBy(member.address.city)
                .having(member.count().gt(1))
                .fetch().stream().forEach(System.out::println);
                /*
                select
                    m1_0.city,
                    count(m1_0.member_id) 
                from
                    TB_MEMBER m1_0 
                left join
                    TB_ORDERS ol1_0 
                        on m1_0.member_id=ol1_0.member_id
                        and ol1_0.order_id=?
                group by
                    m1_0.city 
                having
                    count(m1_0.member_id)>?
                 */
                /*
                [강원도, 4]
                [대전, 2]
                [서울, 2]
                [천안, 2]
                 */
    }

    @Test
    public void fetchJoin() throws Exception {
        QOrder orders=QOrder.order;
        queryFactory
                .select(member)
                .from(member)
                .leftJoin(member.orderList)
                .fetchJoin()//패치조인적용
                .stream().forEach(System.out::println);
                /*
                    Hibernate:
                        select
                            m1_0.member_id,
                            m1_0.city,
                            m1_0.street,
                            m1_0.zipcode,
                            m1_0.createdBy,
                            m1_0.createdDate,
                            m1_0.lastModifiedBy,
                            m1_0.lastModifiedDate,
                            m1_0.member_name,
                            ol1_0.member_id,
                            ol1_0.order_id,
                            ol1_0.delivery_id,
                            ol1_0.order_date,
                            ol1_0.reg_date,
                            ol1_0.reg_id,
                            ol1_0.status,
                            ol1_0.update_date,
                            ol1_0.update_id
                        from
                            TB_MEMBER m1_0
                        left join
                            TB_ORDERS ol1_0
                                on m1_0.member_id=ol1_0.member_id
                    com.example.domain.jpashop.Member@5b265379
                    com.example.domain.jpashop.Member@44acce5d
                    com.example.domain.jpashop.Member@38db8089
                    com.example.domain.jpashop.Member@6183dd2
                    com.example.domain.jpashop.Member@3b5299a3
                    com.example.domain.jpashop.Member@7e8c465d
                    com.example.domain.jpashop.Member@426ee667
                    com.example.domain.jpashop.Member@ff03384
                    com.example.domain.jpashop.Member@2073d71b
                    com.example.domain.jpashop.Member@2c492b03
                    com.example.domain.jpashop.Member@5dd88334
                    com.example.domain.jpashop.Member@40f95b87
                    com.example.domain.jpashop.Member@50dfac19
                 */
    }
    /*
    서브쿼리 : JPAExpressions
    컬럼,where절 두군데서 다 쓸 수 있지만,
    from절에서 인라인뷰로 서브쿼리를 쓸 수 없음.
    
    해결방안
    1. 서브쿼리를 join으로 변경한다. (가능한 상황도 있고, 불가능한 상황도 있다.)
    2. 애플리케이션에서 쿼리를 2번 분리해서 실행한다.
    3. nativeSQL을 사용한다.
     */
    @Test
    public void subQuery() throws Exception {
        QMember memberSub = new QMember("memberSub");
        queryFactory
                .select(member.memberId,member.memberName)
                .from(member)
                .where(member.memberId.eq(
                        JPAExpressions
                                .select(memberSub.memberId.max())
                                .from(memberSub)
                ))
                .stream().forEach(System.out::println);
                /*
                Hibernate:
                select
                    m1_0.member_id,
                    m1_0.member_name
                from
                    TB_MEMBER m1_0
                where
                    m1_0.member_id=(
                        select
                            max(m2_0.member_id)
                        from
                            TB_MEMBER m2_0
                    )
                  [13, 태양]
                 */
    }

    /*
    단순 case와
    new CaseBuilder()
     */
    @Test
    public void caseTest() throws Exception {
                queryFactory
                .select(member.address.city
                        .when("천안").then("충청도")
                        .when("안양").then("경기도")
                        .otherwise("기타").as("지역")
                        )
                .from(member)
                .fetch().stream().forEach(System.out::println);

                queryFactory
                .select(new CaseBuilder()
                        .when(member.memberId.between(0,5)).then("0~5")
                        .when(member.memberId.between(5,10)).then("5~10")
                        .otherwise("기타")
                )
                .from(member)
                .fetch().stream().forEach(System.out::println);


                System.out.println("rank============================");
                NumberExpression<Integer> rankPath = new CaseBuilder()
                .when(member.memberId.between(0, 5)).then(2)
                .when(member.memberId.between(5, 10)).then(1)
                .otherwise(3);

                queryFactory
                .select(member.memberId,member.memberName)
                .from(member)
                .orderBy(rankPath.desc())
                .fetch().stream().forEach(System.out::println);
    }

    /*
    * Expression.constant 상수 사용
    * concat(문자합치기)
    * */
    @Test
    public void Express() throws Exception {
        //상수타입 선언
        Tuple result = queryFactory
                .select(member.memberName, Expressions.constant("A"))
                .from(member)
                .fetchFirst();

        System.out.println("result.toString() = " + result.toString());

        //문자열 합치기
        String result2 = queryFactory
                .select(member.memberName.concat("_").concat(member.memberId.stringValue()))
                .from(member)
                .where(member.memberName.eq("김사과"))
                .fetchOne();

        System.out.println("result2.toString() = " + result2.toString());

    }

}

