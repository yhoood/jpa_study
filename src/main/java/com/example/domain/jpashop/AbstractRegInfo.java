package com.example.domain.jpashop;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AbstractRegInfo {
    @Column(name="reg_id")
    private String regId;

    @Column(name="reg_date")
    private LocalDateTime regDate;

    @Column(name="update_id")
    private String updateId;

    @Column(name="update_date")
    private LocalDateTime updateDate;

}
