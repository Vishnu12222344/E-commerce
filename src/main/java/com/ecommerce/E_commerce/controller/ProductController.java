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
            String uploadDir = System.getProperty("user.dir") + "/uploads/images/";
            String fileName = imageFile.getOriginalFilename();

            if (fileName != null && !fileName.isBlank()) {
                Path filePath = Paths.get(uploadDir + fileName);
                Files.createDirectories(filePath.getParent());
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                product.setImageName(fileName);
            }

            Category fullCategory = productService.getCategoryById(product.getCategory().getId());
            product.setCategory(fullCategory);

            productService.saveProduct(product);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/home";
    }
}
