package com.spring.cosmos.ebookstore.cart.model;

public interface CartService {
    public void removeItemFromCart(String cartId, String itemId);
    public Integer getNumberOfItemsInTheCart(String id);
    public void deleteCart(String id, String partitionKey);
    public void addItemToCart(String cartId, CartItem item);
    public Cart getCart(String id);
}
