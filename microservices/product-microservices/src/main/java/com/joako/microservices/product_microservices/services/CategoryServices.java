package com.joako.microservices.product_microservices.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.joako.microservices.product_microservices.models.entities.CategoryEntity;
import com.joako.microservices.product_microservices.models.mappers.CategoryMapper;
import com.joako.microservices.product_microservices.models.requests.CategoryRequest;
import com.joako.microservices.product_microservices.models.responses.CategoryResponse;
import com.joako.microservices.product_microservices.repositories.CategoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServices {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream().map(categoryMapper::entityToResponse).toList();
    }

    public CategoryResponse getCategoryById(Integer id) {

        return categoryRepository
                .findById(id)
                .map(categoryMapper::entityToResponse)
                .orElseThrow();
    }

    public Integer createCategory(CategoryRequest request) {
        CategoryEntity categoryEntity = categoryMapper.requestToEntity(request);
        return categoryRepository.save(categoryEntity).getId();
    }

    public CategoryResponse updateCategory(Integer id, CategoryRequest request) {

        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow();

        categoryEntity.setId(id);
        categoryEntity.setName(request.name());
        categoryEntity.setDescription(request.description());

        try {
            CategoryEntity categoryEntity2 = categoryRepository.save(categoryEntity);
            log.info("A Category was updated");
            return categoryMapper.entityToResponse(categoryEntity2);
        } catch (Exception e) {
            log.error("An error occurred while updating a Category", e);
            return null;
        }
    }

    public void deletCategory(Integer id) {

        if (!categoryRepository.existsById(id)) {
            //poner exception 
        } else {
            try {
                categoryRepository.deleteById(id);
                log.info("A Category was deleted");
            } catch (Exception e) {
                log.error("An error occurred while deleting a Category", e);
            }
        }
    }
}
