package com.example.domain.jpashop;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {
    private String memberName; //회원 이름
    private EnumOrderStatus orderStatus;//주문 상태[ORDER, CANCEL]

}
