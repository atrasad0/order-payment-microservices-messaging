package com.msdemo.oppayment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderStatus {
    PENDING,
    PAID,
    CANCELED
}
