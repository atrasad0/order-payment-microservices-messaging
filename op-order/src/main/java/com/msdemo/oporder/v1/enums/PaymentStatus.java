package com.msdemo.oporder.v1.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentStatus {
    PENDING,       // Aguardando processamento
    COMPLETED,     // Pagamento concluído com sucesso
    FAILED,        // Pagamento falhou
    CANCELLED,     // Pagamento cancelado
    REFUNDED,      // Pagamento reembolsado
    PROCESSING,    // Pagamento em processamento
    EXPIRED        // Pagamento expirado
}