package com.chaitya.ecommerce.kafka;

import com.chaitya.ecommerce.customer.CustomerResponse;
import com.chaitya.ecommerce.order.PaymentMethod;
import com.chaitya.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
