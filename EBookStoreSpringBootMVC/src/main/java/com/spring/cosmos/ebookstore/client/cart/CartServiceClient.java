package com.spring.cosmos.ebookstore.client.cart;

import com.spring.cosmos.ebookstore.model.cart.Cart;
import com.spring.cosmos.ebookstore.model.cart.CartItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "EBOOKSTORESPRINGBOOTCARTSERVICE",fallback = CartServiceClientFallback.class)
public interface CartServiceClient {
    @GetMapping(value = "/ebooks/carts/{cartId}")
    public Cart getCart(@PathVariable String cartId);

    @DeleteMapping(value = "/ebooks/carts/{cartId}/items/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable String cartId, @PathVariable String itemId);

    @PostMapping(value = "/ebooks/carts/{cartId}")
    public ResponseEntity<String>addItem(@RequestBody CartItem item, @PathVariable String cartId);

    @GetMapping(value = "/ebooks/carts/{cartId}/items/count")
    public Integer getNumberOfItemsInTheCart(@PathVariable String cartId);

    @DeleteMapping(value = "/ebooks/carts/{cartId}")
    public ResponseEntity<String> deleteCart(@PathVariable String cartId);

}
