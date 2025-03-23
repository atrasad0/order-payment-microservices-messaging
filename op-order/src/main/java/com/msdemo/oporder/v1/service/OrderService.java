package com.msdemo.oporder.v1.service;

import com.msdemo.oporder.v1.to.OrderTO;

public interface OrderService {
    OrderTO saveOrder(OrderTO orderTO);
}
