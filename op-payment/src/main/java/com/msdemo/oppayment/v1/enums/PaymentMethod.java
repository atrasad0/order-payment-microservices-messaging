package com.msdemo.oppayment.v1.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentMethod {
    CREDIT_CARD,   // Cartão de crédito
    DEBIT_CARD,    // Cartão de débito
    BANK_TRANSFER, // Transferência bancária
    PIX,           // Pagamento via PIX
}
