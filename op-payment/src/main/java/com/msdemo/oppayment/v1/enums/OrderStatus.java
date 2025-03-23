package com.msdemo.oppayment.v1.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderStatus {
    PENDING,
    PAID,
    DELIVERING,
    DELIVERED,
    CANCELED;
}
