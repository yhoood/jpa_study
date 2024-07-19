package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="TB_DELIVERY")
public class Delivery extends AbstractAddrInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="delivery_id")
    private Long deliveryId;

    @Enumerated(EnumType.STRING)
    private EnumDeliveryStatus status;

    public Delivery() {}

    public Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public EnumDeliveryStatus getStatus() {
        return status;
    }

    public void setStatus(EnumDeliveryStatus status) {
        this.status = status;
    }
}
