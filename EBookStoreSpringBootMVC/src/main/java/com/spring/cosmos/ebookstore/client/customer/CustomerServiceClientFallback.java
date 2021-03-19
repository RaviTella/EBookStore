package com.spring.cosmos.ebookstore.client.customer;

import com.spring.cosmos.ebookstore.model.user.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class CustomerServiceClientFallback implements CustomerServiceClient{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public Customer getCustomer(String customerId) {
        logger.warn("Circuit breaker is Open. Customer Service not available.");
        return null;
    }

    @Override
    public Customer createCustomer(@RequestBody Customer customer) {
        logger.warn("unable to add customer: "+ customer.getId());
        logger.warn("Circuit breaker is Open. Customer Service not available.");
        return null;
    }

}
