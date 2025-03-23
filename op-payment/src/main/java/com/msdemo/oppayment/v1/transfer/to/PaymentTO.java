package com.msdemo.oppayment.v1.transfer.to;

import com.msdemo.oppayment.v1.enums.PaymentMethod;
import com.msdemo.oppayment.v1.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentTO {
    private Long id;
    private Long orderId;
    private BigDecimal amountPaid;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private ZonedDateTime paymentDate;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

}
