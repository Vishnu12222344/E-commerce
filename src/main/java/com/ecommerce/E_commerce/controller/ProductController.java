package com.ecommerce.E_commerce.controller;

import com.ecommerce.E_commerce.entity.Category;
import com.ecommerce.E_commerce.entity.Product;
import com.ecommerce.E_commerce.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", productService.getAllCategories());
        return "add_product";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute Product product,
                             @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            // More robust path construction
            Path uploadPath = Paths.get(System.getProperty("user.dir"), "uploads", "images");
            String fileName = imageFile.getOriginalFilename();

            if (fileName != null && !fileName.isBlank()) {
                Path filePath = uploadPath.resolve(fileName); // Use resolve to combine paths
                Files.createDirectories(uploadPath); // Create directories if they don't exist
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                product.setImageName(fileName);
            }

            Category fullCategory = productService.getCategoryById(product.getCategory().getId());
            product.setCategory(fullCategory);

            // You might want to set the seller here if the logged-in user is the seller
            // For example:
            // User currentUser = userService.getCurrentUser();
            // product.setSeller(currentUser);

            productService.saveProduct(product);

        } catch (IOException e) {
            e.printStackTrace();
            // Consider adding error handling for the user here (e.g., model.addAttribute("error", "Failed to upload image"))
        }

        return "redirect:/home";
    }

    // New method for deleting a product
    @PostMapping("/delete/{productId}")
    public String deleteProduct(@PathVariable Long productId) {
        // Here, you might want to add authorization logic:
        // 1. Get the current authenticated user.
        // 2. Check if the current user is an admin OR if the product's seller matches the current user.

        // For now, simply call the service to delete
        productService.deleteProduct(productId); // This method needs to be added to ProductService

        return "redirect:/home"; // Redirect back to the home page
    }
}