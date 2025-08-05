package com.joako.microservices.product_microservices.models.mappers;

import org.springframework.stereotype.Component;

import com.joako.microservices.product_microservices.models.entities.CategoryEntity;
import com.joako.microservices.product_microservices.models.requests.CategoryRequest;
import com.joako.microservices.product_microservices.models.responses.CategoryResponse;

@Component
public class CategoryMapper {

    public CategoryEntity requestToEntity(CategoryRequest request){
        return CategoryEntity.builder()
                .id(request.id())
                .name(request.name())
                .description(request.description())
                .build();
    }

        public CategoryResponse entityToResponse(CategoryEntity categoryEntity){
            //No uso builder por que no es una clase es un record
            return new CategoryResponse(
                categoryEntity.getId(),
                categoryEntity.getName(),
                categoryEntity.getDescription(),
                categoryEntity.getProducts().stream().map(ProductMapper::entityToResponse).toList()
            );
    }

}
