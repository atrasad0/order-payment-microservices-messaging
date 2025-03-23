package com.msdemo.oporder.v1.repository;

import com.msdemo.oporder.v1.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
