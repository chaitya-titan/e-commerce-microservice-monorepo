package com.chaitya.ecommerce.order;

import com.chaitya.ecommerce.customer.CustomerClient;
import com.chaitya.ecommerce.exceptions.BusinessException;
import com.chaitya.ecommerce.orderline.OrderLine;
import com.chaitya.ecommerce.orderline.OrderLineRequest;
import com.chaitya.ecommerce.orderline.OrderLineService;
import com.chaitya.ecommerce.product.ProductClient;
import com.chaitya.ecommerce.product.PurchaseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository repository;
    private final OrderLineService orderLineService;

    private final OrderMapper mapper;

    public Integer createOrder(OrderRequest request) {
        var customer = customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Customer not found"));

        this.productClient.purchaseProduct(request.products());

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


        //todo: setup kafka

        return 1;
    }
}
