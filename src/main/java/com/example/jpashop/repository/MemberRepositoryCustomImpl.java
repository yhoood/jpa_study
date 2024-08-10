package com.example.jpashop.repository;

import com.example.domain.jpashop.QMember;
import com.example.jpashop.dto.MemberDto;
import com.example.jpashop.dto.QMemberDto;
import com.example.jpashop.parameter.MemberSearchParameter;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.ArrayList;
import java.util.List;

import static com.example.domain.jpashop.QMember.member;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Override
    public List<MemberDto> searchByBuilder(MemberSearchParameter param) {

        BooleanBuilder builder = new BooleanBuilder();
        if(hasText(param.getMemberName())) builder.and(member.memberName.eq(param.getMemberName()));
        if(hasText(param.getCity())) builder.and(member.address.city.like(param.getCity()));
        if(param.getAgeGoe()!=null) builder.and(member.memberAge.goe(param.getAgeGoe()));
        if(param.getAgeLoe()!=null) builder.and(member.memberAge.loe(param.getAgeLoe()));

        return queryFactory
//                .select(Projections.constructor(MemberDto.class
//                        ,member.memberName
//                        ,member.memberAge
//                        ,member.address.city
//                        ,member.address.street
//                        ,member.address.zipcode))
                .select(new QMemberDto(
                        member.memberName
                        ,member.memberAge
                        ,member.address.city
                        ,member.address.street
                        ,member.address.zipcode))
                .from(member)
                .where(builder)
                .fetch();
    }



    @Override
    public List<MemberDto> searchWhereParam(MemberSearchParameter param) {
        return queryFactory
                .select(new QMemberDto(
                        member.memberName
                        ,member.memberAge
                        ,member.address.city
                        ,member.address.street
                        ,member.address.zipcode))
                .from(member)
                .where(memberNameEq(param.getMemberName())
                       ,hasText(param.getCity()) ? member.address.city.like('%'+param.getCity()+'%') : null //재사용 안할경우
                        //,cityLike(pCity)
                       ,ageGoe(param.getAgeGoe())
                       ,ageLoe(param.getAgeLoe()))
                .fetch();
    }

    @Override
    public Long searchCount(MemberSearchParameter param) {
        return queryFactory
                .select(member.count())
                .from(member)
                .where(memberNameEq(param.getMemberName())
                        ,cityLike(param.getCity())
                        ,ageGoe(param.getAgeGoe())
                        ,ageLoe(param.getAgeLoe()))
                .fetchOne();
    }

    @Override
    public Page<MemberDto> searchPageComplex(MemberSearchParameter param, Pageable pageable) {

//        pageable.getSort().stream().forEach(sort -> {
//            Order order = sort.isAscending() ? Order.ASC : Order.DESC;
//            String property = sort.getProperty();
//
//            Path<Object> target = Expressions.path(Object.class, QBoard.board, property);
//            OrderSpecifier<?> orderSpecifier = new OrderSpecifier(order, target);
//            query.orderBy(orderSpecifier);
//        });

        List<MemberDto> content = queryFactory
                        .select(new QMemberDto(
                                member.memberName
                                ,member.memberAge
                                ,member.address.city
                                ,member.address.street
                                ,member.address.zipcode))
                        .from(member)
                        .where(memberNameEq(param.getMemberName())
                                ,cityLike(param.getCity())
                                ,ageGoe(param.getAgeGoe())
                                ,ageLoe(param.getAgeLoe()))
                        //.orderBy(pageable.getSort()) //Querydsl에서는 페이징처리시 orderby가 잘 먹지 않는다.
                        //Sort를 Querydsl의 OrderSpecifier로 변환해야함.
                        //.orderBy(getOrderSpecifiersList(pageable))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        //[기존버전]
//                  long count = queryFactory
//                        .select(member.count())
//                        .from(member)
//                        .where(memberNameEq(param.getMemberName())
//                                ,cityLike(param.getCity())
//                                ,ageGoe(param.getAgeGoe())
//                                ,ageLoe(param.getAgeLoe()))
//                        .fetchOne();
//        return new PageImpl<>(content, pageable, count);

        JPAQuery<Long> countQuery = queryFactory
                        .select(member.count())
                        .from(member)
                        .where(memberNameEq(param.getMemberName())
                                ,cityLike(param.getCity())
                                ,ageGoe(param.getAgeGoe())
                                ,ageLoe(param.getAgeLoe()));
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne); 
        // 마지막 파라미터는 LongSupplier타입으로 받아야함(인터페이스 타입), 따라서 마지막은 람다식으로 작성

        /*
        * PagebleExcutionUtils
        *
        * ```
        스프링 데이터 라이브러리가 제공
        count 쿼리가 생략 가능한 경우 생략해서 처리
                페이지 시작이면서 컨텐츠 사이즈가 페이지 사이즈보다 작을 때
                마지막 페이지 일 때 (offset + 컨텐츠 사이즈를 더해서 전체 사이즈 구함, 더 정확히는 마지막 페이지이면
                서 컨텐츠 사이즈가 페이지 사이즈보다 작을 때)
        * */
    }

    //재사용 용이
    private BooleanExpression memberNameEq(String memberName) {
        return hasText(memberName) ? member.memberName.eq(memberName) : null;
    }
    private BooleanExpression cityLike(String city) {
        return hasText(city) ? member.address.city.like('%'+city+'%') : null;
    }
    private BooleanExpression ageGoe(Integer ageGoe) {
        return ageGoe == null ? null : member.memberAge.goe(ageGoe);
    }
    private BooleanExpression ageLoe(Integer ageLoe) {
        return ageLoe == null ? null : member.memberAge.loe(ageLoe);
    }


    //OrderSpecifier 객체반환.
    //OrderSpecifier 생성자 : public OrderSpecifier(Order order, Expression<T> target)
    public List<OrderSpecifier<?>> getOrderSpecifiersList(Pageable pageable) {
        List<OrderSpecifier<?>> orderSpecifiersList = new ArrayList<>();

        //sort
        pageable.getSort().stream().forEach(sort -> {
            Order order = sort.isAscending() ? Order.ASC : Order.DESC;

            String property = sort.getProperty();
            Path<Object> target = Expressions.path(Object.class, member, property);
            OrderSpecifier<?> orderSpecifier = new OrderSpecifier(order, target);
            orderSpecifiersList.add(orderSpecifier);
        });
        return orderSpecifiersList;
    }
}
