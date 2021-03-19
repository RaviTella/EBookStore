package com.spring.cosmos.ebookstore.cart.controller;


import com.spring.cosmos.ebookstore.cart.model.Cart;
import com.spring.cosmos.ebookstore.cart.model.CartItem;
import com.spring.cosmos.ebookstore.cart.model.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping(value = "/ebooks/carts/{cartId}")
    public ResponseEntity<String>addItemToCart(@RequestBody CartItem item, @PathVariable String cartId) {
        item.setId(UUID
                .randomUUID()
                .toString());
        item.setQuantity(1);
        logger.info("adding item to cart");
        cartService
                .addItemToCart(cartId, item);
        return new ResponseEntity<>(cartId, HttpStatus.OK);
    }

    @GetMapping(value = "/ebooks/carts/{cartId}")
    public Cart getCart(@PathVariable String cartId) {
        Cart cart = cartService
               .getCart(cartId);
        return cart;

    }

    @GetMapping(value = "/ebooks/carts/{cartId}/items/count")
    public Integer getNumberOfItemsInTheCart(@PathVariable String cartId) {
        return cartService.getNumberOfItemsInTheCart(cartId);

    }

    @DeleteMapping(value = "/ebooks/carts/{cartId}/items/{itemId}")
    public ResponseEntity<String> removeItemFromCart(@PathVariable String cartId, @PathVariable String itemId) {
        logger.info("deleting cart item with id" + itemId);
        cartService
                .removeItemFromCart(cartId, itemId);
        return new ResponseEntity<>(cartId, HttpStatus.OK);
    }

    @DeleteMapping(value = "/ebooks/carts/{cartId}")
    public ResponseEntity<String> deleteCart(@PathVariable String cartId){
        cartService.deleteCart(cartId);
        return new ResponseEntity<>(cartId, HttpStatus.OK);
    }


}
