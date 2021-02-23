package com.spring.cosmos.ebookstore.controller.cart;

import com.spring.cosmos.ebookstore.client.cart.CartServiceClient;
import com.spring.cosmos.ebookstore.model.cart.CartService;
import com.spring.cosmos.ebookstore.model.cart.Cart;
import com.spring.cosmos.ebookstore.model.cart.CartItem;
import com.spring.cosmos.ebookstore.security.SecuredCustomer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class CartController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CartService cartService;
    private final CartServiceClient cartServiceClient;

    @Autowired
    public CartController(CartService cartService, CartServiceClient cartServiceClient) {
        this.cartService = cartService;
        this.cartServiceClient=cartServiceClient;
    }

    @PostMapping(value = "/ebooks/cart/item/add")
    public String addItem(CartItem item, HttpSession session) {
        item.setId(UUID
                .randomUUID()
                .toString());
        item.setQuantity(1);
        logger.info("adding item to cart");
        cartServiceClient.addItem(item,session.getId());
/*        cartService
                .addItemToCart(session.getId(), item);*/
        return "redirect:/ebooks/index";
    }

    @GetMapping(value = "/ebooks/cart")
    public String getCart(Model model, HttpSession session, @AuthenticationPrincipal SecuredCustomer securedUser) {
        Cart cart= cartServiceClient.getCart(session.getId());
        model.addAttribute("customer", securedUser);
        model.addAttribute("cart", cart);
        model.addAttribute("cartItemCount", cart
                .getItems()
                .size());
        return "cart";

    }

    @GetMapping(value = "/ebooks/cart/delete/item/{id}")
    public String deleteItem(@PathVariable String id, HttpSession session) {
        logger.info("deleting cart item with id" + id);
        cartServiceClient.deleteItem(session.getId(),id);
        return "redirect:/ebooks/cart";
    }

}
