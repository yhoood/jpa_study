package com.example.jpashop.service;

import com.example.domain.jpashop.Member;
import com.example.jpashop.dto.MemberDto;
import com.example.jpashop.parameter.MemberSearchParameter;
import com.example.jpashop.repository.MemberRepository;
import com.example.jpashop.repository.MemberSupportRepository;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.domain.jpashop.QMember.member;

@SpringBootTest
@Transactional(readOnly = true)
public class QuerydslQuery {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberSupportRepository memberSupportRepository;
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

    @Test
    public void searchCountTest() throws Exception {
        MemberSearchParameter param = new MemberSearchParameter();
        param.setCity("%cit%");
        param.setAgeGoe(20);

        long count =memberRepository.searchCount(param);
        System.out.println("count = " + count);
    }

    @Test
    public void searchPageComplexTest() throws Exception {
        PageRequest pageRequest = PageRequest.of(0, 2,
                Sort.by(Sort.Direction.DESC,"memberName")
                        .and(Sort.by(Sort.Direction.ASC, "memberAge")));
        MemberSearchParameter param = new MemberSearchParameter();
        param.setCity("city");
        param.setAgeGoe(15);
        param.setAgeLoe(30);

        Page<MemberDto> page = memberRepository.searchPageComplex(param,pageRequest);
        System.out.println(page.getContent());
        System.out.println(page.getContent().size());
        System.out.println(page.getTotalElements());
        System.out.println(page.getNumber());
        System.out.println(page.getTotalPages());
        System.out.println(page.isFirst());
        System.out.println(page.hasNext());
    }

    @Test
    public void searchPaginationTest() throws Exception {
        PageRequest pageRequest = PageRequest.of(0, 2,
                Sort.by(Sort.Direction.DESC,"memberName")
                        .and(Sort.by(Sort.Direction.ASC, "memberAge")));
        MemberSearchParameter param = new MemberSearchParameter();
        param.setCity("city");
        param.setAgeGoe(15);
        param.setAgeLoe(30);

        Page<Member> page = memberSupportRepository.searchPagination(param,pageRequest);
        System.out.println(page.getContent());
        System.out.println(page.getContent().size());
        System.out.println(page.getTotalElements());
        System.out.println(page.getNumber());
        System.out.println(page.getTotalPages());
        System.out.println(page.isFirst());
        System.out.println(page.hasNext());
    }

    
    //테스트 필요
    @Test
    public void getOrderSpecifiersListTest() throws Exception {
        PageRequest pageRequest = PageRequest.of(0, 2,
                Sort.by(Sort.Direction.DESC,"memberName")
                        .and(Sort.by(Sort.Direction.ASC,"memberAge")));
        
        List<OrderSpecifier<?>> orderSpecifiersList = new ArrayList<>();
        //sort
        pageRequest.getSort().stream().forEach(sort -> {
            Order order = sort.isAscending() ? Order.ASC : Order.DESC;

            String property = sort.getProperty();
            Path<Object> target = Expressions.path(Object.class, member, property);
            OrderSpecifier<?> orderSpecifier = new OrderSpecifier(order, target);
            orderSpecifiersList.add(orderSpecifier);
        });
        OrderSpecifier[] array = orderSpecifiersList.stream().toArray(OrderSpecifier[]::new);
        System.out.println("Arrays.stream(array).toArray() = " + Arrays.stream(array).toString());
        System.out.println("==============================");
        orderSpecifiersList.stream().forEach(System.out::println);

    }
}
