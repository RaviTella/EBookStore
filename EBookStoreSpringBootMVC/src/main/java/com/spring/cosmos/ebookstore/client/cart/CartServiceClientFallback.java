package com.spring.cosmos.ebookstore.client.cart;

import com.spring.cosmos.ebookstore.model.cart.Cart;
import com.spring.cosmos.ebookstore.model.cart.CartItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CartServiceClientFallback implements CartServiceClient{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public Cart getCart(String cartId) {
        return null;
    }

    @Override
    public ResponseEntity<String> deleteItem(String cartId, String itemId) {
        return new ResponseEntity<>(cartId, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> addItem(CartItem item, String cartId) {
        logger.warn("Circuit breaker is Open. Cart Service not available.");
        return new ResponseEntity<String>("123456789", HttpStatus.OK);
    }

    @Override
    public Integer getNumberOfItemsInTheCart(String cartId) {
        logger.warn("Circuit breaker is Open. Cart Service not available.");
        return -1;
    }

    @Override
    public ResponseEntity<String> deleteCart(String cartId) {
        return new ResponseEntity<>(cartId, HttpStatus.OK);
    }
}
