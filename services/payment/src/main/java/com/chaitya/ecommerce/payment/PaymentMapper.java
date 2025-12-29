package com.chaitya.ecommerce.payment;

import org.springframework.stereotype.Service;

@Service
public class PaymentMapper {
    public Payment ToPayment(PaymentRequest request) {
        return Payment.builder()
                .orderId(request.id())
                .paymentMethod(request.paymentMethod())
                .amount(request.amount())
                .build();
    }
}
