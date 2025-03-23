package com.msdemo.oppayment.service.impl;

import com.msdemo.oppayment.entity.Payment;
import com.msdemo.oppayment.enums.PaymentStatus;
import com.msdemo.oppayment.record.OrderRecord;
import com.msdemo.oppayment.reposiroty.PaymentRepository;
import com.msdemo.oppayment.service.PaymentService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;


    @Override
    public void processOrderPayment(@NonNull OrderRecord orderRecord) {
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

        paymentRepository.save(payment);

        /* chamada de metodo que simula checkout de pagamento */
        this.fakeCheckout(payment).thenAccept(this.paymentRepository::save);

        /* Enviar mensagem para o topico de pagamento finalizado e dar notificação para usuario*/
        /* Atualizar status order */
    }

    @Override
    @KafkaListener(topics = "${kafka.topic.new-order}", containerFactory = "orderKafkaListenerContainerFactory")
    public void consumeOrderAndProcessPayment(OrderRecord orderRecord) {
        this.processOrderPayment(orderRecord);
    }

    CompletableFuture<Payment> fakeCheckout(@NonNull Payment payment) {
        return CompletableFuture.supplyAsync(()-> {
            this.threadSleep(5000);
            payment.setPaymentDate(ZonedDateTime.now());
            payment.setPaymentStatus(PaymentStatus.COMPLETED);

            return payment;
        });
    }

    private void threadSleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
