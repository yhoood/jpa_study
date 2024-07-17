package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="TB_ORDER_ITEM")
public class OrderItem extends AbstractRegInfo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_item_id")
    private Long orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "order_price")
    private int orderPrice;

    @Column(name = "order_count")
    private int orderCount;

    public OrderItem() {}

//    public void injectOrder(Order order) {
//        this.order = order;
//        order.getOrderItemList().add(this);
//    }
    public void injectItem(Item item) {
        this.item = item;
        item.getOderItemList().add(this);
    }
}
