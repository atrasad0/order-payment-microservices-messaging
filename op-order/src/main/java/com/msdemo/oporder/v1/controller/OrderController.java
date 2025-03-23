package com.msdemo.oporder.v1.controller;

import com.msdemo.oporder.v1.service.OrderService;
import com.msdemo.oporder.v1.to.OrderTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    ResponseEntity<OrderTO> post(@Valid @RequestBody OrderTO order ) {
        return ResponseEntity.ok(orderService.saveOrder(order));
    }
}
