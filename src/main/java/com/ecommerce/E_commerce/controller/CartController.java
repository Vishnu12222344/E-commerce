package com.ecommerce.E_commerce.controller;

import com.ecommerce.E_commerce.entity.User;
import com.ecommerce.E_commerce.service.CartService;
import com.ecommerce.E_commerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @PostMapping("/add/{productId}")
    public String addToCart(@PathVariable Long productId, @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails) {
        User user = userService.findByEmail(userDetails.getUsername()).orElse(null);
        cartService.addToCart(user, productId);
        return "redirect:/cart/view";
    }

    @GetMapping("/view")
    public String viewCart(@AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails, Model model) {
        User user = userService.findByEmail(userDetails.getUsername()).orElse(null);
        model.addAttribute("items", cartService.getCartItems(user));
        return "cart";
    }
}
