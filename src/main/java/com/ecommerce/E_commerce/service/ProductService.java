package com.ecommerce.E_commerce.service;

import com.ecommerce.E_commerce.entity.Category;
import com.ecommerce.E_commerce.entity.Product;
import com.ecommerce.E_commerce.repository.CategoryRepository;
import com.ecommerce.E_commerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // ✅ Add this method to resolve the error
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }
}
