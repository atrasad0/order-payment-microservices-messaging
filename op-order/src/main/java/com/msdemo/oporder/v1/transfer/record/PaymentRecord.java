package com.msdemo.oporder.v1.transfer.record;

import com.msdemo.oporder.v1.enums.PaymentMethod;
import com.msdemo.oporder.v1.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public record PaymentRecord(
        Long id,
        Long orderId,
        BigDecimal amountPaid,
        PaymentMethod paymentMethod,
        PaymentStatus paymentStatus,
        ZonedDateTime paymentDate,
        ZonedDateTime createdAt,
        ZonedDateTime updatedAt){}
