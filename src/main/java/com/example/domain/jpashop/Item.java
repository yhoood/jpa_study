package com.example.domain.jpashop;

import com.example.exception.NotEnoughStockException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="item_type")
@Table(name = "TB_ITEM")
public abstract class Item {

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

    //==비즈니스 로직==//
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}