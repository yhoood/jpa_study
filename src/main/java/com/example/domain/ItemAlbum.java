package com.example.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
