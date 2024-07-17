package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_MEMBER")
public class Member extends AbstractAddrInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long memberId;

    @Column(name="member_name")
    private String memberName;

    @OneToMany(mappedBy = "member")
    private List<Order> OrderList=new ArrayList<>();

    public Member() {}

    public Long getMemberId() {
        return memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public List<Order> getOrderList() {
        return OrderList;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public void setOrderList(List<Order> orderList) {
        OrderList = orderList;
    }
}
