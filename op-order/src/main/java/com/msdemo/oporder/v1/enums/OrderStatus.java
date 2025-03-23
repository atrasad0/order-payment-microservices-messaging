package com.msdemo.oporder.v1.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Getter
public enum OrderStatus {
    PENDING,
    PAID,
    DELIVERING,
    DELIVERED,
    CANCELED;
}
