package com.example.jpashop.repository;

import com.example.common.Querydsl4RepositorySupport;
import com.example.domain.jpashop.Member;

import com.example.jpashop.dto.QMemberDto;
import com.example.jpashop.parameter.MemberSearchParameter;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.domain.jpashop.QMember.member;
import static org.springframework.util.StringUtils.hasText;

public class MemberTestRepository extends Querydsl4RepositorySupport {
    public MemberTestRepository() {
        super(Member.class);
    }
    public List<Member> basicSelect() {
        return select(member)
                .from(member)
                .fetch();
    }
    public List<Member> basicSelectFrom() {
        return selectFrom(member)
                .fetch();
    }
    public Page<Member> applyPagination(MemberSearchParameter param, Pageable pageable) {
        return applyPagination(pageable, contentQuery -> contentQuery
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
                ,countQuery -> countQuery
                        .select(member.count())
                        .from(member)
                        .where(memberNameEq(param.getMemberName())
                                ,hasText(param.getCity()) ? member.address.city.like('%'+param.getCity()+'%') : null //재사용 안할경우
                                //,cityLike(pCity)
                                ,ageGoe(param.getAgeGoe())
                                ,ageLoe(param.getAgeLoe()))
        );
    }
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
