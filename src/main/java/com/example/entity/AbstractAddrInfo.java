package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@MappedSuperclass
@Data
public abstract class AbstractAddrInfo {
    @Column(name="city")
    private String city;

    @Column(name="street")
    private String street;

    @Column(name="zipcode")
    private String zipcode;

}
