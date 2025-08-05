package com.joako.microservices.product_microservices.models.mappers;

import com.joako.microservices.product_microservices.models.entities.ProductEntity;
import com.joako.microservices.product_microservices.models.responses.ProductResponse;

public class ProductMapper {

    public static ProductResponse entityToResponse (ProductEntity productEntity){
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
}
