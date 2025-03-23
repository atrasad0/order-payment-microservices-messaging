package com.msdemo.oporder.v1.controller;

import com.msdemo.oporder.v1.service.OrderService;
import com.msdemo.oporder.v1.transfer.record.UpdateOrderStatus;
import com.msdemo.oporder.v1.transfer.to.OrderTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderTO> get(@PathVariable Long id ) {
        log.info("GET Order ID: {}", id);
        return orderService.findOrderById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OrderTO> post(@Valid @RequestBody OrderTO order ) {
        log.info("POST Order: {}", order);
        val saved = orderService.saveOrder(order);

        val location  = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping("/status")
    public ResponseEntity<OrderTO> updateOrderStatus(@Valid @RequestBody UpdateOrderStatus updateOrderStatus) {
        log.info("PUT Status. Order ID: {}, Status: {}", updateOrderStatus.id(), updateOrderStatus.orderStatus());
        return ResponseEntity.ok(orderService.updateOrderStatus(updateOrderStatus));
    }
}
