package com.example.jpashop.service;

import com.example.jpashop.parameter.MemberSearchParameter;
import com.example.jpashop.repository.MemberRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional(readOnly = true)
public class QuerydslQuery {
    @Autowired
    MemberRepository memberRepository;
    /*
    프로젝션 : 컬럼 개별 호출
     */
    @Test
    public void booleanBuilderTest() throws Exception {
        MemberSearchParameter param = new MemberSearchParameter();
        param.setCity("%cit%");
        param.setAgeGoe(20);

        memberRepository.searchByBuilder(param).stream().forEach(System.out::println);
    }
    @Test
    public void whereParamTest() throws Exception {
        MemberSearchParameter param = new MemberSearchParameter();
        param.setCity("city");
        param.setAgeGoe(15);
        param.setAgeLoe(30);
        memberRepository.searchWhereParam(param).stream().forEach(System.out::println);
    }
}
