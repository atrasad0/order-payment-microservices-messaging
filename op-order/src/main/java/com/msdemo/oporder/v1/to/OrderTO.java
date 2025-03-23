package com.msdemo.oporder.v1.to;

import com.msdemo.oporder.v1.enums.OrderStatus;
import com.msdemo.oporder.v1.enums.PaymentMethod;
import com.msdemo.oporder.v1.enums.PaymentStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
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
public class OrderTO {
        private Long id;
        @NotNull(message = "Total amount must be provided")
        @DecimalMin(value = "0.0", message = "Total amount must be greater than zero")
        private BigDecimal totalAmount;

        private OrderStatus orderStatus;

        @NotNull(message = "Payment method must be provided")
        private PaymentMethod paymentMethod;

        private PaymentStatus paymentStatus;

        private ZonedDateTime orderDate;

        private ZonedDateTime updatedAt;
}
