package com.spring.cosmos.ebookstore.cart.controller;


import com.spring.cosmos.ebookstore.cart.model.Cart;
import com.spring.cosmos.ebookstore.cart.model.CartItem;
import com.spring.cosmos.ebookstore.cart.model.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

@RestController
public class CartController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping(value = "/ebooks/cart/{cartId}")
    public ResponseEntity<String>addItem(@RequestBody CartItem item, @PathVariable String cartId) {
        item.setId(UUID
                .randomUUID()
                .toString());
        item.setQuantity(1);
        logger.info("adding item to cart");
        cartService
                .addItemToCart(cartId, item);
        return new ResponseEntity<>(cartId, HttpStatus.OK);
    }

    @GetMapping(value = "/ebooks/cart/{cartId}")
    public Cart getCart(@PathVariable String cartId) {
        Cart cart = cartService
               .getCart(cartId);
        return cart;

    }

    @DeleteMapping(value = "/ebooks/cart/{cartId}/item/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable String cartId, @PathVariable String itemId) {
        logger.info("deleting cart item with id" + itemId);
        cartService
                .removeItemFromCart(cartId, itemId);
        return new ResponseEntity<>(cartId, HttpStatus.OK);
    }

}
