package com.ecommerce.E_commerce.controller;

import com.ecommerce.E_commerce.entity.User;
import com.ecommerce.E_commerce.entity.Order; // <-- Add this import
import com.ecommerce.E_commerce.service.CartService;
import com.ecommerce.E_commerce.service.UserService;
import com.ecommerce.E_commerce.service.OrderService; // <-- Add this import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired private CartService cartService;
    @Autowired private UserService userService;
    @Autowired private OrderService orderService; // <-- Add this line to inject OrderService

    @GetMapping("/view")
    public String viewCart(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("items", cartService.getCartItems(user));
        model.addAttribute("cartTotal", cartService.getCartTotal(user)); // New: Add cart total
        return "cart";
    }

    @PostMapping("/add/{productId}")
    public String addToCart(@PathVariable Long productId) {
        User user = userService.getCurrentUser();
        cartService.addToCart(user, productId);
        return "redirect:/cart/view";
    }

    @PostMapping("/increase/{productId}")
    public String increaseQuantity(@PathVariable Long productId) {
        User user = userService.getCurrentUser();
        cartService.addToCart(user, productId);
        return "redirect:/cart/view";
    }


    @PostMapping("/decrease/{productId}")
    public String decreaseQuantity(@PathVariable Long productId) {
        User user = userService.getCurrentUser();
        cartService.decreaseQuantity(user, productId);
        return "redirect:/cart/view";
    }

    @PostMapping("/remove/{productId}")
    public String removeFromCart(@PathVariable Long productId) {
        User user = userService.getCurrentUser();
        cartService.removeFromCart(user, productId);
        return "redirect:/cart/view";
    }

    @PostMapping("/proceedToBuy")
    public String proceedToBuy() {
        User user = userService.getCurrentUser();
        if (user == null) {
            return "redirect:/login"; // Redirect to login if user not found
        }
        try {
            Order order = orderService.createOrderFromCart(user);
            return "redirect:/payment/" + order.getId(); // Redirect to payment gateway page
        } catch (IllegalStateException e) {
            // Handle empty cart or other business logic errors
            System.err.println("Error creating order: " + e.getMessage());
            return "redirect:/cart/view?error=" + e.getMessage(); // Add error message to cart view
        } catch (IllegalArgumentException e) {
            // Handle stock issues
            System.err.println("Error creating order: " + e.getMessage());
            return "redirect:/cart/view?error=" + e.getMessage();
        }
    }
}