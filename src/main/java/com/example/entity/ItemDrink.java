package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="TB_ITEM_DRINK")
@DiscriminatorValue("DRINK")
public class ItemDrink extends Item{
    @Column(name="drink_name")
    private String drinkName;

    @Column(name="drink_ml")
    private int ml;

    public ItemDrink() {}

    public ItemDrink(String drinkName, int ml) {
        this.drinkName = drinkName;
        this.ml = ml;
    }
}
