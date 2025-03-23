package com.msdemo.oporder.v1.feignclient;

import com.msdemo.oporder.v1.transfer.record.PaymentRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "op-payment", path = "/api/v1/payments")
public interface PaymentFeignClient {

    @GetMapping
    ResponseEntity<PaymentRecord> get(@RequestParam("orderId") Long orderId);
}
