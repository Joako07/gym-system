package com.joako.microservices.product_microservices.models.mappers;

import org.springframework.stereotype.Component;

import com.joako.microservices.product_microservices.models.entities.CategoryEntity;
import com.joako.microservices.product_microservices.models.entities.ProductEntity;
import com.joako.microservices.product_microservices.models.requests.ProductRequest;
import com.joako.microservices.product_microservices.models.responses.ProductResponse;

@Component
public class ProductMapper {

    public ProductResponse entityToResponse (ProductEntity productEntity){
        return new ProductResponse(
            productEntity.getId(),
            productEntity.getName(),
            productEntity.getDescription(),
            productEntity.getPrice(),
            productEntity.getStock(),
            productEntity.getImageUrl(),
            productEntity.getCategory().getId(),
            productEntity.getCategory().getName(),
            productEntity.getCategory().getDescription()
        );
    }

    public ProductEntity requestToEntity(ProductRequest productRequest){
        return ProductEntity.builder()
            .id(productRequest.id())
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .stock(productRequest.stock())
                .imageUrl(productRequest.imageUrl())
                .category(CategoryEntity.builder()
                        .id(productRequest.categoryId())
                        .build())
                .build();

    }
}
