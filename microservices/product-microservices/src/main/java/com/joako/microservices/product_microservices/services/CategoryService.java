package com.joako.microservices.product_microservices.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joako.microservices.product_microservices.exceptions.ApiErrorException;
import com.joako.microservices.product_microservices.exceptions.NotFoundApiException;
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
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream().map(categoryMapper::entityToResponse).toList();
    }

    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Integer id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::entityToResponse)
                .orElseThrow(() -> new NotFoundApiException("404 Not Found - Category with ID " + id + " not found",
                        "No se encontro la categoria con el Id " + id));
    }

    @Transactional
    public Integer createCategory(CategoryRequest request) {
        CategoryEntity categoryEntity = categoryMapper.requestToEntity(request);
        try {
            categoryRepository.save(categoryEntity);
            log.info("A Category was created");
            return categoryEntity.getId();
        } catch (Exception e) {
            log.error("An error occurred while creating a Category", e);
            throw ApiErrorException.builder()
                    .error(e.getMessage())
                    .message("Ocurrió un error al crear una categoria")
                    .build();
        }

    }

    @Transactional
    public CategoryResponse updateCategory(Integer id, CategoryRequest request) {

        CategoryEntity categoryEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundApiException("404 Not Found - Category with ID " + id + " not found",
                        "No se encontro la categoria con el Id " + id));

        categoryEntity.setId(id);
        categoryEntity.setName(request.name());
        categoryEntity.setDescription(request.description());

        try {
            CategoryEntity categoryEntity2 = categoryRepository.save(categoryEntity);
            log.info("A Category was updated");
            return categoryMapper.entityToResponse(categoryEntity2);
        } catch (Exception e) {
            log.error("An error occurred while updating a Category", e);
            throw ApiErrorException.builder()
                    .error(e.getMessage())
                    .message("Ocurrió un error al actualizar una categoria")
                    .build();
        }
    }

    @Transactional
    public void deletCategory(Integer id) {

        if (!categoryRepository.existsById(id)) {
            throw new NotFoundApiException("404 Not Found - Category with ID " + id + " not found",
                    "No se encontro la categoria con el Id " + id);
        } else {
            try {
                categoryRepository.deleteById(id);
                log.info("A Category was deleted");
            } catch (Exception e) {
                log.error("An error occurred while deleting a Category", e);
                throw ApiErrorException.builder()
                        .error(e.getMessage())
                        .message("Ocurrió un error al borrar una categoria")
                        .build();
            }
        }
    }
}
