package com.example.domain.jpashop;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@Getter @Setter //실무에서는 setter 사용시 변경지점을 파악하기 힘들기에 setter대신 메소드를 별도 제공 해야함.
@Table(name = "TB_MEMBER")
@NamedQuery(
        name="Member.namedFindByMemberName",
        query="select m from Member m where m.memberName = :memberName")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long memberId;

    @Column(name="member_name")
    private String memberName;

    @Column(name="member_age")
    private Integer memberAge;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orderList=new ArrayList<>();

    public Member(String memberName) {
        this.memberName = memberName;
    }

    public Member(String memberName, Address address) {
        this.memberName = memberName;
        this.address = address;
    }

    public Member(String memberName,Integer memberAge, Address address) {
        this.memberName = memberName;
        this.memberAge = memberAge;
        this.address = address;
    }

    public void changeName(String memberName){
        this.memberName = memberName;
    }

    public static Member createMember(String memberName){
        return new Member(memberName);
    }

}
