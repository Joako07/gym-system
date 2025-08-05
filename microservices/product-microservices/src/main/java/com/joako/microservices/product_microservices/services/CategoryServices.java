package com.joako.microservices.product_microservices.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.joako.microservices.product_microservices.models.entities.CategoryEntity;
import com.joako.microservices.product_microservices.models.mappers.CategoryMapper;
import com.joako.microservices.product_microservices.models.requests.CategoryRequest;
import com.joako.microservices.product_microservices.models.responses.CategoryResponse;
import com.joako.microservices.product_microservices.repositories.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServices {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryResponse> getAllCategories(){
        return categoryRepository.findAll().stream().map(categoryMapper::entityToResponse).toList();
    }

    public Integer createCategory(CategoryRequest request){
        CategoryEntity categoryEntity = categoryMapper.requestToEntity(request);
        return categoryRepository.save(categoryEntity).getId();
    }

}
