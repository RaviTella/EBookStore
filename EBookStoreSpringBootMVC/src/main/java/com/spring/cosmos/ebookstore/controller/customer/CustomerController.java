package com.spring.cosmos.ebookstore.controller.customer;

import com.spring.cosmos.ebookstore.client.customer.CustomerServiceClient;
import com.spring.cosmos.ebookstore.model.user.Name;
import com.spring.cosmos.ebookstore.model.user.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class CustomerController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CustomerServiceClient customerServiceClient;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerController(CustomerServiceClient customerServiceClient, PasswordEncoder passwordEncoder) {
        this.customerServiceClient = customerServiceClient;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/ebooks/user/createAccount")
    public String createAccount(@ModelAttribute CustomerForm userForm, Model model) {
       String password = this.passwordEncoder.encode(userForm.getPassword());
      Customer user = new Customer(userForm.getEmail(),password, new Name(userForm.getFirstName(),userForm.getLastName()));
      if(customerServiceClient.getCustomer(userForm.getEmail())!=null){
          logger.info(userForm.getEmail()+ " is already associated with an account");
          model.addAttribute("accountCreationFailed", "Try another email ID");
          return "createaccount";
      }
      logger.info("Adding customer with ID : "+ user.getId());
      customerServiceClient.createCustomer(user);
      return "login";
    }
}
