package com.joako.microservices.product_microservices.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joako.microservices.product_microservices.exceptions.ApiErrorException;
import com.joako.microservices.product_microservices.exceptions.BadRequestApiException;
import com.joako.microservices.product_microservices.exceptions.NotFoundApiException;
import com.joako.microservices.product_microservices.models.entities.ProductEntity;
import com.joako.microservices.product_microservices.models.mappers.ProductMapper;
import com.joako.microservices.product_microservices.models.requests.ProductQuantityRequest;
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

    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(productMapper::entityToResponse).toList();
    }

    @Transactional(readOnly = true)
    public ProductResponse getProductById(Integer id) {
        return productRepository.findById(id)
                .map(productMapper::entityToResponse)
                .orElseThrow(() -> new NotFoundApiException("404 Not Found - Product with ID " + id + " not found",
                        "No se encontro el producto con el Id " + id));
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsByCategoryId(Integer id) {

        if (id == null) {
            throw new NotFoundApiException("404 Not Found - Category with ID " + id + " not found",
                    "No se encontro la categoria con el Id " + id);
        }
        return productRepository.findAll().stream()
                .filter(product -> product.getCategory().getId().equals(id))
                .map(productMapper::entityToResponse)
                .toList();
    }

    @Transactional
    public ProductResponse createProduct(ProductRequest request) {

        // Vemos que la categoria exista
        if (categoryService.getCategoryById(request.categoryId()) == null) {
            throw new NotFoundApiException("404 Not Found - Category with ID " + request.categoryId() + " not found",
                    "No se encontro la categoria con el Id " + request.categoryId());
        }

        ProductEntity productEntity = productMapper.requestToEntity(request);
        try {
            productRepository.save(productEntity);
            log.info("A Product was created");
            return productMapper.entityToResponse(productEntity);
        } catch (Exception e) {
            log.error("An error occurred while creating a Product", e);
            throw ApiErrorException.builder()
                    .error(e.getMessage())
                    .message("Ocurrió un error al crear un producto")
                    .build();
        }
    }

    @Transactional
    public ProductResponse updateProduct(Integer id, ProductRequest request) {

        // Vemos que el producto exista
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundApiException("404 Not Found - Product with ID " + id + " not found",
                        "No se encontro el producto con el Id " + id));

        // Vemos que la categoria exista
        if (categoryService.getCategoryById(request.categoryId()) == null) {
            throw new NotFoundApiException("404 Not Found - Category with ID " + request.categoryId() + " not found",
                    "No se encontro la categoria con el Id " + request.categoryId());
        }

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
            throw ApiErrorException.builder()
                    .error(e.getMessage())
                    .message("Ocurrió un error al actualizar un producto")
                    .build();
        }
    }

    @Transactional
    public void deleteProduct(Integer id) {

        // Acá uso existById por que no necesito traer el producto entero. Esto es mas
        // liviano
        if (!productRepository.existsById(id)) {
            throw new NotFoundApiException("404 Not Found - Product with ID " + id + " not found",
                    "No se encontro el producto con el Id " + id);
        } else {
            try {
                productRepository.deleteById(id);
                log.info("A Product was deleted");
            } catch (Exception e) {
                log.error("An error occurred while deleting a Product", e);
                throw ApiErrorException.builder()
                        .error(e.getMessage())
                        .message("Ocurrió un error al borrar un producto")
                        .build();
            }
        }
    }

    // Al realizar una compra debo restar lo comprado del stock
    @Transactional
    public void purchaseProduct(List<ProductQuantityRequest> request) {
        for (ProductQuantityRequest item : request) {

            // Verifico que el producto exista
            ProductEntity productEntity = productRepository.findById(item.productId())
                    .orElseThrow(() -> new NotFoundApiException(
                            "404 Not Found - Product with ID " + item.productId() + " not found",
                            "No se encontro el producto con el Id " + item.productId()));

            // Verifico que el pedido no sea un número negativo
            if (item.quantity() < 0) {
                throw new BadRequestApiException("400 Bad Request", "La cantidad pedida es un número menor a 0");
            }

            // Verifico que el pedido no sea mayor a lo que tengo en stock
            if (productEntity.getStock() < item.quantity()) {
                throw new BadRequestApiException("400 Bad Request",
                        "La cantidad pedida es mayor a lo que se tiene en stock");
            }

            // Resto lo comprado a lo que tengo en stock
            productEntity.setStock(productEntity.getStock() - item.quantity());

            try {
                productRepository.save(productEntity);
                log.info("A purchase was made");
            } catch (Exception e) {
                log.error("An error occurred while a purchase was made", e);
                throw ApiErrorException.builder()
                        .error(e.getMessage())
                        .message("Ocurrió un error al realizar una compra.")
                        .build();
            }
        }
    }

    // Para actualizar el stock en caso de que ingrese mercaderia
    @Transactional
    public void updateStock(List<ProductQuantityRequest> request) {

        for (ProductQuantityRequest item : request) {

            // Verifico que el producto exista
            ProductEntity productEntity = productRepository.findById(item.productId())
                    .orElseThrow(() -> new NotFoundApiException(
                            "404 Not Found - Product with ID " + item.productId() + " not found",
                            "No se encontro el producto con el Id " + item.productId()));

            // Verifico que lo ingresado no sea un número negativo
            if (item.quantity() < 0) {
                throw new BadRequestApiException("400 Bad Request", "La cantidad ingresada es un número menor a 0");
            }

            // Sumo al stock lo que ingresa
            productEntity.setStock(productEntity.getStock() + item.quantity());

            try {
                productRepository.save(productEntity);
                log.info("The stock was modified successfully");
            } catch (Exception e) {
                log.error("An error occurred while modifying the stock.", e);
                throw ApiErrorException.builder()
                        .error(e.getMessage())
                        .message("Ocurrió un error al modificar el stock.")
                        .build();
            }
        }

    }

}
