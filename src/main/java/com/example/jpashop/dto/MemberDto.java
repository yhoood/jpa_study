package com.example.jpashop.dto;
import com.example.domain.jpashop.Member;
import lombok.Data;

@Data
public class MemberDto {
    private String memberName;
    private String city;
    private String street;
    private String zipcode;

    public MemberDto(String memberName, String city, String street, String zipcode) {
        this.memberName = memberName;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    public MemberDto(Member member) {
        this.memberName = member.getMemberName();
    }
}
