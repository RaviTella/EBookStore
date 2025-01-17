package com.spring.cosmos.ebookstore.cart.model;

import com.azure.cosmos.models.PartitionKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DefaultCartService implements CartService {
private CartRepository cartRepository;

    @Autowired
    public DefaultCartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public void removeItemFromCart(String cartId, String itemId) {
        Cart cart = cartRepository.findById(cartId,new PartitionKey(cartId)).get();
        List<CartItem> items =cart.getItems();
        if(items.size() ==1) {deleteCart(cartId); return;}
        cart.setSubTotal(cart
                .getSubTotal()
                .subtract(getPrice(itemId, cart)));
        items.removeIf(item -> item.getId().equals(itemId));
        cartRepository.save(cart);


    }

    private BigDecimal getPrice(String itemId, Cart cart) {
        return cart
                .getItems()
                .stream()
                .filter(item -> item.id.equals(itemId))
                .findFirst()
                .get()
                .getPrice();
    }

    @Override
    public Integer getNumberOfItemsInTheCart(String cartId) {

        return cartRepository.findById(cartId,new PartitionKey(cartId)).map(cart -> cart.getItems().size()).orElse(0);

    }

    @Override
    public void deleteCart(String id) {
        cartRepository.deleteById(id,new PartitionKey(id));
    }

    @Override
    public void addItemToCart(String cartId, CartItem item) {
        Cart cart = cartRepository.findById(cartId,new PartitionKey(cartId)).orElse(new Cart());
        if(cart.getId()==null) {
            cart.setId(cartId);
            cart.setSubTotal(item.price);
            cart.getItems().add(item);
            cartRepository.save(cart);
            return;
        }
        cart.getItems().add(item);
        cart.setSubTotal(cart
                .getItems()
                .stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        cartRepository.save(cart);

    }

    @Override
    public Cart getCart(String id) {
        return cartRepository.findById(id, new PartitionKey(id)).orElse(new Cart());
    }
}
