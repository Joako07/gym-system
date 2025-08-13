package com.joako.microservices.product_microservices.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.joako.microservices.product_microservices.models.entities.ProductEntity;
import com.joako.microservices.product_microservices.models.mappers.ProductMapper;
import com.joako.microservices.product_microservices.models.requests.ProductRequest;
import com.joako.microservices.product_microservices.models.responses.ProductResponse;
import com.joako.microservices.product_microservices.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductMapper productMapper;

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(productMapper::entityToResponse).toList();
    }

    public ProductResponse getProductById(Integer id) {

        if (id == null) {
            // Lanzar exception;
            return null;
        }

        return productRepository
                .findById(id)
                .map(productMapper::entityToResponse)
                .orElseThrow();
    }

    public ProductResponse createProduct(ProductRequest request) {

        // Vemos que la categoria exista
        if (categoryService.getCategoryById(request.categoryId()) == null) {
            // throw new ProductException("Category with ID %s not
            // found".formatted(product.categoryId()));
            // Lanzar exception
            return null;
        }

        ProductEntity productEntity = productMapper.requestToEntity(request);
        try {
            productRepository.save(productEntity);
            log.info("A Product was created");
            return productMapper.entityToResponse(productEntity);
        } catch (Exception e) {
            // Lanzar exception
            return null;
        }
    }

    public ProductResponse updateProduct(Integer id, ProductRequest request) {

        if (id == null) {
            // Lanzar exception;
            return null;
        }

        // Vemos que la categoria exista
        if (categoryService.getCategoryById(request.categoryId()) == null) {
            // throw new ProductException("Category with ID %s not
            // found".formatted(product.categoryId()));
            // Lanzar exception
            return null;
        }

        ProductEntity productEntity = productRepository.findById(id).orElseThrow();

        productEntity.setName(request.name());
        productEntity.setDescription(request.description());
        productEntity.setPrice(request.price());
        productEntity.setStock(request.stock());
        productEntity.setImageUrl(request.imageUrl());

        try {
            ProductEntity productEntity2 = productRepository.save(productEntity);
            log.info("A Product was updated");
            return productMapper.entityToResponse(productEntity2);
        } catch (Exception e) {
            log.error("An error occurred while updating a Product", e);
            return null;
        }
    }

    public void deleteProduct(Integer id){

        if(!productRepository.existsById(id)){
             //poner exception 
        }else{
            try {
                productRepository.deleteById(id);
                 log.info("A Product was deleted");
            } catch (Exception e) {
                log.error("An error occurred while deleting a Product", e);
            }
        }
    }
}
