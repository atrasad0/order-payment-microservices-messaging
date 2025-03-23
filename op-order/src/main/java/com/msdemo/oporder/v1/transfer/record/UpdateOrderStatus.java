package com.msdemo.oporder.v1.transfer.record;

import com.msdemo.oporder.v1.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateOrderStatus(
    @NotNull(message = "Order ID must be provided")
    Long id,

    @NotNull(message = "Order Status must be provided")
    OrderStatus orderStatus
) { }
