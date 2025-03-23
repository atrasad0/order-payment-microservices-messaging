package com.msdemo.oppayment.v1.service.impl;

import com.msdemo.oppayment.v1.entity.Payment;
import com.msdemo.oppayment.v1.enums.OrderStatus;
import com.msdemo.oppayment.v1.enums.PaymentStatus;
import com.msdemo.oppayment.v1.feignclient.OrderFeignClient;
import com.msdemo.oppayment.v1.transfer.record.OrderRecord;
import com.msdemo.oppayment.v1.reposiroty.PaymentRepository;
import com.msdemo.oppayment.v1.service.PaymentService;
import com.msdemo.oppayment.v1.transfer.record.UpdateOrderStatus;
import com.msdemo.oppayment.v1.transfer.to.PaymentTO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderFeignClient orderFeignClient;


    @Override
    @Transactional
    public void processOrderPayment(@NonNull OrderRecord orderRecord) {
        log.info("Processing order payment. Order ID: {}, Total Amount: {}", orderRecord.id(), orderRecord.totalAmount());

        if (Objects.isNull(orderRecord.paymentMethod())) {
            throw new IllegalArgumentException("Payment Method should be informed in order");
        }

        if (Objects.isNull(orderRecord.id())) {
            throw new IllegalArgumentException("Order ID should be informed");
        }

        val payment = new Payment();

        payment.setOrderId(orderRecord.id());
        payment.setPaymentMethod(orderRecord.paymentMethod());
        payment.setAmountPaid(orderRecord.totalAmount());

        log.info("Saving Payment. Order ID: {}, Amount Paid: {}", orderRecord.id(), orderRecord.totalAmount());
        paymentRepository.save(payment);

        log.info("Payment checkout initiated. Order ID: {}", orderRecord.id());
        /* chamada de metodo que simula checkout de pagamento */
        this.fakeCheckout(payment).thenAccept(p -> {
            this.paymentRepository.save(p);
            log.info("Payment processed successfully. Payment ID: {}", p.getId());

            log.info("Requesting update order status for order ID: {}", orderRecord.id());
            orderFeignClient.updateOrderStatus(new UpdateOrderStatus(orderRecord.id(), OrderStatus.PAID));
        });

        /* Enviar mensagem para o topico de pagamento finalizado e dar notificação para usuario*/
        /* Atualizar status order */
    }

    @Override
    @KafkaListener(topics = "${kafka.topic.new-order}", containerFactory = "orderKafkaListenerContainerFactory")
    public void consumeOrderAndProcessPayment(OrderRecord orderRecord) {
        this.processOrderPayment(orderRecord);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentTO> findByOrderId(@NonNull Long orderId) {
        return paymentRepository.findByOrderId(orderId).map(this::toDto);
    }

    CompletableFuture<Payment> fakeCheckout(@NonNull Payment payment) {
        return CompletableFuture.supplyAsync(()-> {
            this.threadSleep(1000);
            payment.setPaymentDate(ZonedDateTime.now());
            payment.setPaymentStatus(PaymentStatus.COMPLETED);

            return payment;
        });
    }

    public PaymentTO toDto(Payment model) {
        val to = new PaymentTO();
        BeanUtils.copyProperties(model, to);

        return to;
    }

    private void threadSleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
