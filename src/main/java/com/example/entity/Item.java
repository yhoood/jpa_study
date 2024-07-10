package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="item_type")
@Table(name = "TB_ITEM")
public class Item extends AbstractRegInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="item_id")
    private Long itemId;

    @Column(name="item_name")
    private String itemName;

    @Column(name="item_price")
    private int itemPrice;

    @Column(name="item_stock_quantity")
    private int stockQuantity;

    @OneToMany(mappedBy = "item", cascade=CascadeType.PERSIST, orphanRemoval = true)
    private List<OrderItem> oderItemList=new ArrayList<>();

    @ManyToMany(mappedBy = "itemList")
    private List<Category> categoryList=new ArrayList<>();

    public Item() {}
}
