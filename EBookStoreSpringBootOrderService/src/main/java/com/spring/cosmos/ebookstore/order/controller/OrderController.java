package com.spring.cosmos.ebookstore.order.controller;

import com.spring.cosmos.ebookstore.order.model.Order;
import com.spring.cosmos.ebookstore.order.model.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {
    private final OrderRepository orderRepository;


    @Autowired
    public OrderController(OrderRepository orderRepository){
        this.orderRepository=orderRepository;
    }

    @PostMapping(value = "/ebooks/orders")
    public Order createOrder(@RequestBody Order order){
       return  orderRepository.save(order);
    }

    @GetMapping(value = "/ebooks/customers/{customerId}/orders")
    public List<Order> getCustomerOrders(@PathVariable String customerId){
        return orderRepository.getOrdersByCustomerId(customerId);
    }

}
