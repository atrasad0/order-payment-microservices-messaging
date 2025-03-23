package com.msdemo.oppayment.record;

import com.msdemo.oppayment.enums.OrderStatus;
import com.msdemo.oppayment.enums.PaymentMethod;
import com.msdemo.oppayment.enums.PaymentStatus;

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