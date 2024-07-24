package com.example.domain.jpashop;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name="TB_ORDER_ITEM")
public class OrderItem extends AbstractRegInfo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_item_id")
    private Long orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;    //주문

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;      //주문상품

    @Column(name = "order_price")
    private int orderPrice; //주문가격

    @Column(name = "order_count")
    private int orderCount; //주문수량

    public OrderItem() {}

//    public void injectOrder(Order order) {
//        this.order = order;
//        order.getOrderItemList().add(this);
//    }
    public void injectItem(Item item) {
        this.item = item;
        item.getOderItemList().add(this);
    }

    //==생성 메서드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int
            orderCount) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setOrderCount(orderCount);
        item.removeStock(orderCount);
        return orderItem;
    }
    //==비즈니스 로직==//
    /** 주문 취소 */
    public void cancel() {
        getItem().addStock(orderCount);
    }
    //==조회 로직==//
    /** 주문상품 전체 가격 조회 */
    public int getTotalPrice() {
        return getOrderPrice() * getOrderCount();
    }
}
