package com.ecommerce.E_commerce.service;

import com.ecommerce.E_commerce.entity.*;
import com.ecommerce.E_commerce.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired private CartRepository cartRepository;
    @Autowired private CartItemRepository cartItemRepository;
    @Autowired private ProductRepository productRepository;

    public Cart getOrCreateCart(User user) {
        return cartRepository.findByUser(user).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });
    }

    public void addToCart(User user, Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) return;

        Cart cart = getOrCreateCart(user);
        CartItem item = new CartItem();
        item.setCart(cart);
        item.setProduct(product);
        item.setQuantity(1);
        cartItemRepository.save(item);
    }

    public List<CartItem> getCartItems(User user) {
        Cart cart = cartRepository.findByUser(user).orElse(null);
        return (cart != null) ? cart.getItems() : List.of();
    }
}
