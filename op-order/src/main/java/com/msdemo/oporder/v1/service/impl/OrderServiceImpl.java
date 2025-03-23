package com.msdemo.oporder.v1.service.impl;

import com.msdemo.oporder.common.exception.ResourceNotFoundException;
import com.msdemo.oporder.v1.entity.Order;
import com.msdemo.oporder.v1.feignclient.PaymentFeignClient;
import com.msdemo.oporder.v1.repository.OrderRepository;
import com.msdemo.oporder.v1.service.OrderService;
import com.msdemo.oporder.v1.transfer.record.PaymentRecord;
import com.msdemo.oporder.v1.transfer.record.UpdateOrderStatus;
import com.msdemo.oporder.v1.transfer.to.OrderTO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final PaymentFeignClient paymentFeignClient;
    private final KafkaTemplate<String, OrderTO> kafkaTemplateOrder;

    @Value("${kafka.topic.new-order}")
    private String newOrderTopic;
    @Override
    @Transactional
    public OrderTO saveOrder(OrderTO orderTO) {
        Order order = this.toModel(orderTO);

        val savedTO = this.toDto(orderRepository.save(order));
        savedTO.setPaymentMethod(orderTO.getPaymentMethod());

        log.info("Sending new order message. Topic: {}, Order ID: {}", newOrderTopic, order.getId());
        kafkaTemplateOrder.send(newOrderTopic, savedTO);

        return savedTO;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrderTO> findOrderById(Long id) {
        val order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found by ID: " + id));

        val payment = Optional.ofNullable(paymentFeignClient.get(id).getBody())
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found for Order ID: " + id));

        return Optional.of(this.toDto(order, payment));
    }

    @Override
    @Transactional
    public OrderTO updateOrderStatus(@NonNull UpdateOrderStatus updateOrderStatus) {
        val order = orderRepository.findById(updateOrderStatus.id()).orElseThrow(()-> new ResourceNotFoundException("Order not found by ID: " + updateOrderStatus.id()));
        order.setOrderStatus(updateOrderStatus.orderStatus());

        log.info("Updating order status for order ID: {}", order.getId());
        orderRepository.save(order);

        log.info("Requesting order payment infos for order ID: {}", order.getId());
        val payment = paymentFeignClient.get(order.getId()).getBody();

        return this.toDto(order, payment);
    }

    public Order toModel(OrderTO to) {
        val order = new Order();
        BeanUtils.copyProperties(to, order);

        return order;
    }

    public OrderTO toDto(Order model) {
        val to = new OrderTO();
        BeanUtils.copyProperties(model, to);

        return to;
    }

    public OrderTO toDto(Order model, PaymentRecord paymentRecord) {
        val to = new OrderTO();
        BeanUtils.copyProperties(model, to);

        to.setPaymentMethod(paymentRecord.paymentMethod());
        to.setPaymentStatus(paymentRecord.paymentStatus());

        return to;
    }
}
