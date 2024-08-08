package com.example.jpashop.parameter;

import lombok.Data;

@Data
public class MemberSearchParameter {
    private String memberName;
    private String city;
    private Integer ageGoe;
    private Integer ageLoe;
}
