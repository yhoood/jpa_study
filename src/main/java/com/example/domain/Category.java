package com.example.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.*;


@Entity
@Getter @Setter
@Table(name="TB_CATEGORY")
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id")
    private Long categoryId;

    @Column(name="category_name")
    private String categoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> childList=new ArrayList<>();

    @ManyToMany
    @JoinTable(name="TB_CATEGORY_ITEM"
                ,joinColumns = @JoinColumn(name="category_id")
                ,inverseJoinColumns = @JoinColumn(name = "item_id")
            )
    private List<Item> itemList=new ArrayList<>();

    public Category() {}

    //==연관관계 메서드==//
    public void addChildCategory(Category child) {
        this.childList.add(child);
        child.setParent(this);
    }
}
