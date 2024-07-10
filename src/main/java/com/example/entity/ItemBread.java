package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="TB_ITEM_BREAD")
@DiscriminatorValue("BREAD")
public class ItemBread extends Item{

    @Column(name="bread_name")
    private String breadName;

    @Column(name="bread_type")
    private String breadType;

}
