package com.example.jpashop.repository;

import com.example.domain.jpashop.QMember;
import com.example.jpashop.dto.MemberDto;
import com.example.jpashop.dto.QMemberDto;
import com.example.jpashop.parameter.MemberSearchParameter;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

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
                       ,hasText(param.getCity()) ? member.address.city.like(param.getCity()) : null //재사용 안할경우
                        //,cityLike(pCity)
                       ,ageGoe(param.getAgeGoe())
                       ,ageLoe(param.getAgeLoe()))
                .fetch();
    }
    //재사용 용이
    private BooleanExpression memberNameEq(String memberName) {
        return hasText(memberName) ? member.memberName.eq(memberName) : null;
    }
    private BooleanExpression ageGoe(Integer ageGoe) {
        return ageGoe == null ? null : member.memberAge.goe(ageGoe);
    }
    private BooleanExpression ageLoe(Integer ageLoe) {
        return ageLoe == null ? null : member.memberAge.loe(ageLoe);
    }
}
