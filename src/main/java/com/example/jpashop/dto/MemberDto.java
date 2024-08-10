package com.example.jpashop.dto;
import com.example.domain.jpashop.Member;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class MemberDto {
    private String memberName;
    private Integer memberAge;
    private String city;
    private String street;
    private String zipcode;

    public MemberDto(){}

    @QueryProjection
    public MemberDto(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    public MemberDto(String memberName, String city, String street, String zipcode) {
        this.memberName = memberName;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
    @QueryProjection
    public MemberDto(String memberName, Integer memberAge, String city, String street, String zipcode) {
        this.memberName = memberName;
        this.memberAge = memberAge;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    public MemberDto(Member member) {
        this.memberName = member.getMemberName();
        this.memberAge = member.getMemberAge();
        this.city = member.getAddress().getCity();
        this.street = member.getAddress().getStreet();
        this.zipcode = member.getAddress().getZipcode();
    }
}
