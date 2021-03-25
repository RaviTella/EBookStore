package com.spring.cosmos.ebookstore.client.customer;

import com.spring.cosmos.ebookstore.client.order.OrderServiceClientFallback;
import com.spring.cosmos.ebookstore.model.user.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "CUSTOMERSERVICE",fallback = CustomerServiceClientFallback.class)
public interface CustomerServiceClient {

    @GetMapping(value = "/ebooks/customers/{customerId}")
    public Customer getCustomer(@PathVariable String customerId);

    @PostMapping(value ="/ebooks/customers")
    public Customer createCustomer(@RequestBody Customer customer);

}
