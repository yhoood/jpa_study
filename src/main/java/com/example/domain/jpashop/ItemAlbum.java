package com.example.domain.jpashop;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("ALBUM")
//@Table(name="TB_ITEM_ALBUM")
@Getter @Setter
public class ItemAlbum extends Item{
    private String artist;
    private String etc;
}
