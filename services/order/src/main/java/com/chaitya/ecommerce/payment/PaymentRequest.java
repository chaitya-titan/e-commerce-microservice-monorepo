package com.chaitya.ecommerce.payment;

import com.chaitya.ecommerce.customer.CustomerResponse;
import com.chaitya.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
