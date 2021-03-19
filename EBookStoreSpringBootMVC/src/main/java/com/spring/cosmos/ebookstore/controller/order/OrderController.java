package com.spring.cosmos.ebookstore.controller.order;

import com.spring.cosmos.ebookstore.client.cart.CartServiceClient;
import com.spring.cosmos.ebookstore.client.customer.CustomerServiceClient;
import com.spring.cosmos.ebookstore.client.order.OrderServiceClient;
import com.spring.cosmos.ebookstore.model.cart.Cart;
import com.spring.cosmos.ebookstore.model.user.Address;
import com.spring.cosmos.ebookstore.model.user.CreditCard;
import com.spring.cosmos.ebookstore.model.user.Customer;
import com.spring.cosmos.ebookstore.security.SecuredCustomer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class OrderController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final OrderServiceClient orderServiceClient;
    private final CartServiceClient cartServiceClient;
    private final CustomerServiceClient customerServiceClient;
    private final OrderHelper orderHelper;

    @Autowired
    public OrderController(OrderServiceClient orderServiceClient, CartServiceClient cartServiceClient,CustomerServiceClient customerServiceClient, OrderHelper orderHelper) {
        this.orderServiceClient = orderServiceClient;
        this.cartServiceClient = cartServiceClient;
        this.customerServiceClient=customerServiceClient;
        this.orderHelper = orderHelper;
    }

    @PostMapping(value = "/ebooks/order/create")
    public String createOrder(OrderForm orderForm, Model model, HttpSession session, @AuthenticationPrincipal SecuredCustomer securedUser) {
        logger.info("Creating order");
        orderServiceClient.createOrder(orderHelper.getOrderFromOrderForm(orderForm));
        if (orderForm.getStreetAddress() != null) {
            Customer user = getCustomerUsingOrderFormDetails(orderForm);
            customerServiceClient.createCustomer(user);
            securedUser.setAddress(user.getAddress());
            securedUser.setCreditCardNumber(user.getCreditCard());
        }
        cartServiceClient.deleteCart(session.getId());
        model.addAttribute("customer", securedUser);
        return "orderconfirmation";

    }

    @PostMapping(value = "/ebooks/order/checkout")
    public String checkOut(@ModelAttribute Cart cart, Model model, HttpSession session, @AuthenticationPrincipal SecuredCustomer securedUser) {

        model.addAttribute("customer", securedUser);
        model.addAttribute("order", orderHelper.getOrder(cart, securedUser.getUsername()));
        //todo why should this go to cart service. cart is already present in memory
        model.addAttribute("cartItemCount", cartServiceClient.getNumberOfItemsInTheCart(session.getId()));
        return "checkout";
    }


    @GetMapping(value = "/ebooks/order/customer/{customerId}")
    public String getCustomerOrders(@PathVariable String customerId, Model model, HttpSession session, @AuthenticationPrincipal SecuredCustomer securedUser) {
        model.addAttribute("customer", securedUser);
        logger.info("Getting order for customer with id "+ customerId );
        model.addAttribute("orders", orderServiceClient.getCustomerOrders(customerId));
        model.addAttribute("cartItemCount", cartServiceClient.getNumberOfItemsInTheCart(session.getId()));
        return "orders";
    }

    Customer getCustomerUsingOrderFormDetails(OrderForm orderForm) {
        Customer customer = customerServiceClient
                .getCustomer(orderForm.getCustomerId());
        customer.setId(orderForm.getCustomerId());
        Address address = new Address(orderForm.getStreetAddress(), orderForm.getCity(), orderForm.getState(), orderForm.getZip(), "USA");
        CreditCard creditCard = new CreditCard(orderForm.getCreditCardNumber(),"Master","12/2025","123");
        customer.setAddress(address);
        customer.setCreditCard(creditCard);
        return customer;
    }



}
