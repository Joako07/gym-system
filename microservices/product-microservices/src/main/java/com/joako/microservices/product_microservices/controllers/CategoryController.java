package com.joako.microservices.product_microservices.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joako.microservices.product_microservices.models.requests.CategoryRequest;
import com.joako.microservices.product_microservices.models.responses.CategoryResponse;
import com.joako.microservices.product_microservices.services.CategoryServices;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryServices categoryServices;

    @GetMapping()
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        return ResponseEntity.ok(categoryServices.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Integer id) {
        return ResponseEntity.ok(categoryServices.getCategoryById(id));
    }
    
    @PostMapping()
    public ResponseEntity<Integer> createCategory(@Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoryServices.createCategory(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Integer id,@Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoryServices.updateCategory(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletCategory(@PathVariable Integer id){
        return ResponseEntity.accepted().build();
    }
}
