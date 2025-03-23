package com.msdemo.oppayment.v1.transfer.record;

import com.msdemo.oppayment.v1.enums.OrderStatus;
import com.msdemo.oppayment.v1.enums.PaymentMethod;
import com.msdemo.oppayment.v1.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public record OrderRecord(
        Long id,
        BigDecimal totalAmount,
        OrderStatus orderStatus,
        PaymentMethod paymentMethod,
        PaymentStatus paymentStatus,
        ZonedDateTime orderDate,
        ZonedDateTime updatedAt
) { }