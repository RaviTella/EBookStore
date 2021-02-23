package com.spring.cosmos.ebookstore.controller.order;

import com.azure.cosmos.models.PartitionKey;
import com.spring.cosmos.ebookstore.model.cart.Cart;
import com.spring.cosmos.ebookstore.model.order.LineItem;
import com.spring.cosmos.ebookstore.model.order.Order;
import com.spring.cosmos.ebookstore.model.user.Address;
import com.spring.cosmos.ebookstore.model.user.CreditCard;
import com.spring.cosmos.ebookstore.model.user.Customer;
import com.spring.cosmos.ebookstore.model.user.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderHelper {

    Order getOrder(Cart cart, String customerId) {
        Order order = new Order();
        order.setCustomerId(customerId);
        order.setStatus("SHIPPED");
        order.setSubTotal(cart.getSubTotal());
        List<LineItem> lineItems = new ArrayList<>();
        order.setLineItems(lineItems);
        cart
                .getItems()
                .forEach(cartItem -> {
                    LineItem lineItem = new LineItem();
                    lineItem.setId(cartItem.getId());
                    lineItem.setTitle(cartItem.getTitle());
                    lineItem.setDescription(cartItem.getDescription());
                    lineItem.setAuthor(cartItem.getAuthor());
                    lineItem.setImage(cartItem.getImage());
                    lineItem.setQuantity(cartItem.getQuantity());
                    lineItem.setPrice(cartItem.getPrice());
                    lineItems.add(lineItem);
                });
        return order;
    }

    Order getOrderFromOrderForm(OrderForm orderForm) {
        Order order = new Order();
        order.setCustomerId(orderForm.getCustomerId());
        order.setStatus("RECEIVED");
        order.setSubTotal(orderForm.getSubTotal());
        List<LineItem> lineItems = new ArrayList<>();
        order.setLineItems(lineItems);
        orderForm
                .getLineItems()
                .forEach(item -> {
                    LineItem lineItem = new LineItem();
                    lineItem.setId(item.getId());
                    lineItem.setTitle(item.getTitle());
                    lineItem.setDescription(item.getDescription());
                    lineItem.setAuthor(item.getAuthor());
                    lineItem.setImage(item.getImage());
                    lineItem.setQuantity(item.getQuantity());
                    lineItem.setPrice(item.getPrice());
                    lineItems.add(lineItem);
                });
        return order;
    }

}
