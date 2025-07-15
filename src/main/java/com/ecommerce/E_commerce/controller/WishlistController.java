package com.ecommerce.E_commerce.controller;

import com.ecommerce.E_commerce.entity.User;
import com.ecommerce.E_commerce.service.UserService;
import com.ecommerce.E_commerce.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private UserService userService;

    @PostMapping("/add/{productId}")
    public String addToWishlist(@PathVariable Long productId, @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails) {
        User user = userService.findByEmail(userDetails.getUsername()).orElse(null);
        wishlistService.addToWishlist(user, productId);
        return "redirect:/wishlist/view";
    }

    @GetMapping("/view")
    public String viewWishlist(@AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails, Model model) {
        User user = userService.findByEmail(userDetails.getUsername()).orElse(null);
        model.addAttribute("items", wishlistService.getWishlistItems(user));
        return "wishlist";
    }
}
