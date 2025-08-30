package com.joako.microservices.class_microservices.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joako.microservices.class_microservices.exceptions.ApiErrorException;
import com.joako.microservices.class_microservices.exceptions.NotFoundApiException;
import com.joako.microservices.class_microservices.models.entities.ClassEntity;
import com.joako.microservices.class_microservices.models.mappers.ClassMapper;
import com.joako.microservices.class_microservices.models.requests.ClassRequest;
import com.joako.microservices.class_microservices.models.responses.ClassResponse;
import com.joako.microservices.class_microservices.repositories.ClassRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClassService {

    private final ClassRepository classRepository;
    private final ClassMapper classMapper;

    // GETALL
    @Transactional(readOnly = true)
    public List<ClassResponse> getAllClasses() {
        return classRepository.findAll().stream().map(classMapper::entityToResponse).toList();
    }

      // GETBYID
    @Transactional(readOnly = true)
    public ClassResponse getClassById(Integer id) {
        return classRepository.findById(id)
                .map(classMapper::entityToResponse)
                .orElseThrow(() -> new NotFoundApiException("404 Not Found - Class with ID " + id + " not found",
                        "No se encontro la clase con el Id " + id));
    }

    //CREATE
    @Transactional
    public Integer createClass(ClassRequest request) {
        ClassEntity classEntity = classMapper.requestToEntity(request);
        try {
            classRepository.save(classEntity);
            log.info("A Class was created");
            return classEntity.getId();
        } catch (Exception e) {
            log.error("An error occurred while creating a Class", e);
            throw ApiErrorException.builder()
                    .error(e.getMessage())
                    .message("Ocurrió un error al crear una clase")
                    .build();
        }
    }
    
    //UPDATE
    @Transactional
    public ClassResponse updateClass(Integer id, ClassRequest request) {

        ClassEntity classEntity = classRepository.findById(id)
                .orElseThrow(() -> new NotFoundApiException("404 Not Found - Class with ID " + id + " not found",
                        "No se encontro la clase con el Id " + id));

        classEntity.setId(id);
        classEntity.setName(request.name());
    
        try {
            ClassEntity classEntity2 = classRepository.save(classEntity);
            log.info("A Class was updated");
            return classMapper.entityToResponse(classEntity2);
        } catch (Exception e) {
            log.error("An error occurred while updating a Class", e);
            throw ApiErrorException.builder()
                    .error(e.getMessage())
                    .message("Ocurrió un error al actualizar una clase")
                    .build();
        }
    }

    @Transactional
    public void deletClass(Integer id) {

        if (!classRepository.existsById(id)) {
            throw new NotFoundApiException("404 Not Found - Class with ID " + id + " not found",
                    "No se encontro la clase con el Id " + id);
        } else {
            try {
                classRepository.deleteById(id);
                log.info("A Class was deleted");
            } catch (Exception e) {
                log.error("An error occurred while deleting a Class", e);
                throw ApiErrorException.builder()
                        .error(e.getMessage())
                        .message("Ocurrió un error al borrar una clase")
                        .build();
            }
        }
    }
}
