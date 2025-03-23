package com.msdemo.oppayment.reposiroty;

import com.msdemo.oppayment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
