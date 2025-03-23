package com.msdemo.oppayment.v1.service;

import com.msdemo.oppayment.v1.transfer.record.OrderRecord;
import com.msdemo.oppayment.v1.transfer.to.PaymentTO;

import java.util.Optional;

public interface PaymentService {
    void processOrderPayment(OrderRecord orderRecord);

    void consumeOrderAndProcessPayment(OrderRecord orderRecord);

    Optional<PaymentTO> findByOrderId(Long orderId);
}
