package com.spring.cosmos.ebookstore.security;

import com.spring.cosmos.ebookstore.client.customer.CustomerServiceClient;
import com.spring.cosmos.ebookstore.model.user.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerDetailService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
     private CustomerServiceClient customerServiceClient;

    @Autowired
    public CustomerDetailService(CustomerServiceClient customerServiceClient) {
        this.customerServiceClient = customerServiceClient;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer user = null;
        try {
        user = customerServiceClient.getCustomer(email);
        if (user==null) {
            throw new UsernameNotFoundException(email);
        }
        } catch(UsernameNotFoundException e) {
            logger.error(e.toString());
            throw e;
        }
        return new SecuredCustomer(user);
    }
}
