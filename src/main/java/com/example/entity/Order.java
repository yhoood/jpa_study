package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.apache.logging.log4j.util.Lazy;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name="TB_ORDERS")
public class Order extends AbstractRegInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id")
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="delivery_id")
    private Delivery delivery;

//    @OneToMany(mappedBy = "order")
//    private List<OrderItem> OrderItemList=new ArrayList<>();

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

    public Long getOrderId() {
        return orderId;
    }

    public Member getMember() {
        return member;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public EnumOrderStatus getStatus() {
        return status;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public void setStatus(EnumOrderStatus status) {
        this.status = status;
    }
}
