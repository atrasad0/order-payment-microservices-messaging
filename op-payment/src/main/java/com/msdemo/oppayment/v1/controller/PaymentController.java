package com.msdemo.oppayment.v1.controller;

import com.msdemo.oppayment.v1.service.PaymentService;
import com.msdemo.oppayment.v1.transfer.to.PaymentTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;
    @GetMapping
    public ResponseEntity<PaymentTO> get(@RequestParam("orderId") Long orderId) {
        log.info("GET Payment OrderId: {}", orderId);
        return paymentService.findByOrderId(orderId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
