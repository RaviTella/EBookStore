package com.spring.cosmos.ebookstore.client.order;

import com.spring.cosmos.ebookstore.model.order.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderServiceClientFallback implements OrderServiceClient{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public List<Order> getCustomerOrders(String customerId) {
        logger.warn("Circuit breaker is Open. Order Service not available.");
        return null;
    }

    @Override
    public Order createOrder(Order order) {
        logger.warn("Circuit breaker is Open. Order Service not available.");
        return null;
    }
}
