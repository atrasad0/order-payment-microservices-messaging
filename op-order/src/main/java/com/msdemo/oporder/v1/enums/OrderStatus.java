package com.msdemo.oporder.v1.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderStatus {
    PENDING,
    PAID,
    DELIVERING,
    DELIVERED,
    CANCELED
}
