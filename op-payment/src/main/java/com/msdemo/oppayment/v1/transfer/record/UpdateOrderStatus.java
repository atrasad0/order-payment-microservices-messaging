package com.msdemo.oppayment.v1.transfer.record;


import com.msdemo.oppayment.v1.enums.OrderStatus;

public record UpdateOrderStatus(Long id, OrderStatus orderStatus) { }
