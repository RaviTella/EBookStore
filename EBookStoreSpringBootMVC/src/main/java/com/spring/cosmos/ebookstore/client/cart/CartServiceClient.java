package com.spring.cosmos.ebookstore.client.cart;

import com.spring.cosmos.ebookstore.model.cart.Cart;
import com.spring.cosmos.ebookstore.model.cart.CartItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "EBOOKSTORESPRINGBOOTCARTSERVICE",fallback = CartServiceClientFallback.class)
public interface CartServiceClient {
    @GetMapping(value = "/ebooks/cart/{cartId}")
    public Cart getCart(@PathVariable String cartId);

    @DeleteMapping(value = "/ebooks/cart/{cartId}/item/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable String cartId, @PathVariable String itemId);

    @PostMapping(value = "/ebooks/cart/{cartId}")
    public ResponseEntity<String>addItem(@RequestBody CartItem item, @PathVariable String cartId);
}
