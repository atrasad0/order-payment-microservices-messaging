package com.msdemo.oporder.v1.service;

import com.msdemo.oporder.v1.transfer.record.UpdateOrderStatus;
import com.msdemo.oporder.v1.transfer.to.OrderTO;

import java.util.Optional;

public interface OrderService {
    OrderTO saveOrder(OrderTO orderTO);

    Optional<OrderTO> findOrderById(Long id);

    OrderTO updateOrderStatus(UpdateOrderStatus updateOrderStatus);
}
