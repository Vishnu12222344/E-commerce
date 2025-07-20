package com.ecommerce.E_commerce.service;

import com.ecommerce.E_commerce.entity.*;
import com.ecommerce.E_commerce.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal; // Import BigDecimal
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
        if (user == null) return;
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) return;

        Cart cart = getOrCreateCart(user);

        // Ensure the collection is not null, especially if newly created or lazily loaded
        if (cart.getItems() == null) {
            cart.setItems(new java.util.ArrayList<>());
        }

        CartItem existing = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + 1);
            cartItemRepository.save(existing);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(1);
            cartItemRepository.save(newItem);
        }
    }

    public List<CartItem> getCartItems(User user) {
        Cart cart = cartRepository.findByUser(user).orElse(null);
        // Ensure the items collection is not null before returning
        return (cart != null && cart.getItems() != null) ? cart.getItems() : List.of();
    }

    public void decreaseQuantity(User user, Long productId) {
        if (user == null) return;
        Cart cart = cartRepository.findByUser(user).orElse(null);
        if (cart == null) return;

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst().orElse(null);

        if (item != null) {
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                cartItemRepository.save(item);
            } else {
                // If quantity becomes 0, remove the item
                cart.getItems().remove(item); // Remove from the collection
                cartItemRepository.delete(item); // Explicitly delete the CartItem
                // Or if using orphanRemoval=true on Cart @OneToMany:
                // cartRepository.save(cart); // This would trigger deletion
            }
        }
    }

    public void removeFromCart(User user, Long productId) {
        if (user == null) return;
        Cart cart = cartRepository.findByUser(user).orElse(null);
        if (cart == null) return;

        // Use iterator for safe removal during iteration or stream filter to find and then remove
        CartItem itemToRemove = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (itemToRemove != null) {
            cart.getItems().remove(itemToRemove); // Remove from the collection
            cartItemRepository.delete(itemToRemove); // Explicitly delete the CartItem
            // Or if using orphanRemoval=true on Cart @OneToMany:
            // cartRepository.save(cart); // This would trigger deletion
        }
    }

    // New: Calculate the total price of items in the cart
    public BigDecimal getCartTotal(User user) {
        Cart cart = cartRepository.findByUser(user).orElse(null);
        if (cart == null || cart.getItems().isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : cart.getItems()) {
            if (item.getProduct() != null && item.getProduct().getPrice() != null) {
                // Calculate item total: price * quantity
                BigDecimal itemTotal = item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                total = total.add(itemTotal);
            }
        }
        return total;
    }
}