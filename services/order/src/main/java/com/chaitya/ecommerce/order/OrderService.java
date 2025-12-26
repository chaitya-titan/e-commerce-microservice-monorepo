package com.chaitya.ecommerce.order;

import com.chaitya.ecommerce.customer.CustomerClient;
import com.chaitya.ecommerce.exceptions.BusinessException;
import com.chaitya.ecommerce.kafka.OrderConfirmation;
import com.chaitya.ecommerce.kafka.OrderProducer;
import com.chaitya.ecommerce.orderline.OrderLineRequest;
import com.chaitya.ecommerce.orderline.OrderLineService;
import com.chaitya.ecommerce.product.ProductClient;
import com.chaitya.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository repository;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;

    private final OrderMapper mapper;

    public Integer createOrder(OrderRequest request) {
        var customer = customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Customer not found"));

        var purchasedProduct = this.productClient.purchaseProduct(request.products());

        var order = repository.save(
                mapper.toOrder(request)
        );

        for (PurchaseRequest purchaseRequest:  request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        //todo: payment

        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProduct
                )
        );

        return order.getId();
    }

    public List<OrderResponse> findAllOrders() {
        return repository.findAll()
                .stream()
                .map(mapper::fromOrder)
                .toList();
    }

    public OrderResponse findById(Integer orderId) {
        return repository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }
}
