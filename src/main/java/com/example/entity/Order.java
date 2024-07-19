package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name="TB_ORDERS")
public class Order extends AbstractRegInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id")
    private Long orderId;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @OneToOne
    @JoinColumn(name="delivery_id")
    private Delivery delivery;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> OrderItemList=new ArrayList<>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="order_date")
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private EnumOrderStatus status;

//    public void addOrderItem(OrderItem orderItem) {
//        OrderItemList.add(orderItem);
//        orderItem.setOrder(this);
//    }

    public Order() {}

    
}
