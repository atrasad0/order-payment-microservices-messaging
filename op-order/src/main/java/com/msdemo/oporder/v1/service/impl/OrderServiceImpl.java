package com.msdemo.oporder.v1.service.impl;

import com.msdemo.oporder.v1.entity.Order;
import com.msdemo.oporder.v1.repository.OrderRepository;
import com.msdemo.oporder.v1.service.OrderService;
import com.msdemo.oporder.v1.to.OrderTO;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, OrderTO> kafkaTemplateOrder;

    @Value("${kafka.topic.new-order}")
    private String newOrderTopic;
    @Override
    @Transactional
    public OrderTO saveOrder(OrderTO orderTO) {
        Order order = this.toModel(orderTO);

        val savedTO = this.toDto(orderRepository.save(order));
        savedTO.setPaymentMethod(orderTO.getPaymentMethod());

        kafkaTemplateOrder.send(newOrderTopic, savedTO);

        return savedTO;
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
}
