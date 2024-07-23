package com.example.domain.jpashop;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
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
