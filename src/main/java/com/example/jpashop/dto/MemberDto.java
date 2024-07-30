package com.example.jpashop.dto;
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
}
