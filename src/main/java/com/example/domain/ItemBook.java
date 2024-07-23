package com.example.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("BOOK")
//@Table(name="TB_ITEM_BOOK")
@Getter @Setter
public class ItemBook extends Item{

    private String author;
    private String isbn;

}
