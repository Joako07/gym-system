package com.joako.microservices.product_microservices.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joako.microservices.product_microservices.models.requests.ProductQuantityRequest;
import com.joako.microservices.product_microservices.models.requests.ProductRequest;
import com.joako.microservices.product_microservices.models.responses.ProductResponse;
import com.joako.microservices.product_microservices.services.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping()
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/category/{id}")
    public List<ProductResponse> getProductsByCategoryId(@PathVariable Integer id) {
    return productService.getProductsByCategoryId(id);
    }


    @PostMapping()
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Integer id,
            @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/purchase")
    public ResponseEntity<Void> purchaseProduct(@Valid @RequestBody List<ProductQuantityRequest> request) {
        productService.purchaseProduct(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/setstock")
    public ResponseEntity<Void> updateProductStock(@Valid @RequestBody List<ProductQuantityRequest> request) {
        productService.updateStock(request);
        return ResponseEntity.ok().build();
    }

}
