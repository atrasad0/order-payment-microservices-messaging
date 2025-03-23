package com.msdemo.oppayment.service;

import com.msdemo.oppayment.record.OrderRecord;

import java.util.Optional;

public interface PaymentService {
    void processOrderPayment(OrderRecord orderRecord);

    void consumeOrderAndProcessPayment(OrderRecord orderRecord);
}
