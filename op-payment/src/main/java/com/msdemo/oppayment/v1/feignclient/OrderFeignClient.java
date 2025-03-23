package com.msdemo.oppayment.v1.feignclient;

import com.msdemo.oppayment.v1.transfer.record.OrderRecord;
import com.msdemo.oppayment.v1.transfer.record.UpdateOrderStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(name = "op-order", path = "/api/v1/orders")
public interface OrderFeignClient {

    @PutMapping("/status")
    ResponseEntity<OrderRecord> updateOrderStatus(@RequestBody UpdateOrderStatus updateOrderStatus);
}
