package com.ecommerce.E_commerce.service;

import com.ecommerce.E_commerce.entity.User;
import com.ecommerce.E_commerce.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder; // <-- Import this
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // <-- Inject PasswordEncoder

    // Constructor Injection is preferred for required dependencies
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Add or modify this register method
    public void register(User user) {
        // Set a default role if not provided (e.g., "ROLE_USER")
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("ROLE_USER"); // Or "USER", depending on your security config
        }
        // IMPORTANT: Encode the password before saving!
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Spring Security uses username, which is email in your case

        Optional<User> optionalUser = userRepository.findByEmail(email);
        // Handle case where user is not found more gracefully, or ensure it cannot happen after authentication
        return optionalUser.orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
}