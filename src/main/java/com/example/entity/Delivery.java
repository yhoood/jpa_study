package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="TB_DELIVERY")
public class Delivery extends AbstractAddrInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="delivery_id")
    private Long deliveryId;

    @Enumerated(EnumType.STRING)
    private EnumDeliveryStatus status;

    public Delivery() {}
}
