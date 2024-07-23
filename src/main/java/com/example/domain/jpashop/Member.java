package com.example.domain.jpashop;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity
@Getter @Setter //실무에서는 setter 사용시 변경지점을 파악하기 힘들기에 setter대신 메소드를 별도 제공 해야함.
@Table(name = "TB_MEMBER")
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long memberId;

    @Column(name="member_name")
    private String memberName;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orderList=new ArrayList<>();

    public Member() {}
}
