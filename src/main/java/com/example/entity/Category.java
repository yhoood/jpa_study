package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.*;


@Entity
@Data
@Table(name="TB_CATEGORY")
public class Category extends AbstractRegInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id")
    private Long categoryId;

    @Column(name="category_name")
    private String categoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child=new ArrayList<>();

    @ManyToMany
    @JoinTable(name="TB_CATEGORY_ITEM"
                ,joinColumns = @JoinColumn(name="category_id")
                ,inverseJoinColumns = @JoinColumn(name = "item_id")
            )
    private List<Item> itemList=new ArrayList<>();

    public Category() {}
}
