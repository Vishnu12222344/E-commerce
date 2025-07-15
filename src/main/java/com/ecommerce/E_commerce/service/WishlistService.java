package com.ecommerce.E_commerce.service;

import com.ecommerce.E_commerce.entity.*;
import com.ecommerce.E_commerce.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    @Autowired private WishlistRepository wishlistRepository;
    @Autowired private WishlistProductRepository wishlistProductRepository;
    @Autowired private ProductRepository productRepository;

    public Wishlist getOrCreateWishlist(User user) {
        return wishlistRepository.findByUser(user).orElseGet(() -> {
            Wishlist wishlist = new Wishlist();
            wishlist.setUser(user);
            return wishlistRepository.save(wishlist);
        });
    }

    public void addToWishlist(User user, Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) return;

        Wishlist wishlist = getOrCreateWishlist(user);
        WishlistProduct wp = new WishlistProduct();
        wp.setWishlist(wishlist);
        wp.setProduct(product);
        wishlistProductRepository.save(wp);
    }

    public List<WishlistProduct> getWishlistItems(User user) {
        Wishlist wishlist = wishlistRepository.findByUser(user).orElse(null);
        return (wishlist != null) ? wishlist.getProducts() : List.of();
    }
}
