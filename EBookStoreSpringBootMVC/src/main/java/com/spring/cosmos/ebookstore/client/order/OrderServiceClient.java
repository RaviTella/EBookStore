package com.spring.cosmos.ebookstore.client.order;

import com.spring.cosmos.ebookstore.client.cart.CartServiceClientFallback;
import com.spring.cosmos.ebookstore.model.order.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "EBOOKSTORESPRINGBOOTORDERSERVICE",fallback = OrderServiceClientFallback.class)
public interface OrderServiceClient {

    @GetMapping(value = "/ebooks/customers/{customerId}/orders")
    public List<Order> getCustomerOrders(@PathVariable String customerId);

    @PostMapping(value = "/ebooks/orders")
    public Order createOrder(@RequestBody Order order);

}
