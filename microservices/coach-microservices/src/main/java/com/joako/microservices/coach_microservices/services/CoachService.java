package com.joako.microservices.coach_microservices.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joako.microservices.coach_microservices.exceptions.ApiErrorException;
import com.joako.microservices.coach_microservices.exceptions.NotFoundApiException;
import com.joako.microservices.coach_microservices.models.entities.CoachEntity;
import com.joako.microservices.coach_microservices.models.mappers.CoachMapper;
import com.joako.microservices.coach_microservices.models.requests.CoachRequest;
import com.joako.microservices.coach_microservices.models.responses.CoachResponse;
import com.joako.microservices.coach_microservices.repositories.CoachRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CoachService {

    private final CoachRepository coachRepository;
    private final CoachMapper coachMapper;

    // GETALL
    @Transactional(readOnly = true)
    public List<CoachResponse> getAllCoaches() {
        return coachRepository.findAll().stream().map(coachMapper::entityToResponse).toList();
    }

    // GETONE
    @Transactional(readOnly = true)
    public CoachResponse getCoachById(Long id) {
        return coachRepository.findById(id)
                .map(coachMapper::entityToResponse)
                .orElseThrow(() -> new NotFoundApiException("404 Not Found - Coach with ID " + id + " not found",
                        "No se encontro el entrenador con el Id " + id));
    }

    // CREATE
    @Transactional
    public CoachResponse createCoach(CoachRequest request) {

        CoachEntity coachEntity = coachMapper.requestToEntity(request);
        try {
            coachRepository.save(coachEntity);
            log.info("A Coach was created");
            return coachMapper.entityToResponse(coachEntity);
        } catch (Exception e) {
            log.error("An error occurred while creating a Coach", e);
            throw ApiErrorException.builder()
                    .error(e.getMessage())
                    .message("Ocurrió un error al crear un entrenador")
                    .build();
        }
    }

    // UPDATE
    @Transactional
    public CoachResponse updateCoach(Long id, CoachRequest request) {

        // Vemos que el entrenador exista
        CoachEntity coachEntity = coachRepository.findById(id)
                .orElseThrow(() -> new NotFoundApiException("404 Not Found - Coach with ID " + id + " not found",
                        "No se encontro el entrenador con el Id " + id));

        coachEntity.setName(request.name());
        coachEntity.setLastName(request.lastName());
        coachEntity.setCellphone(request.cellphone());

        try {
            CoachEntity coachEntity2 = coachRepository.save(coachEntity);
            log.info("A Coach was updated");
            return coachMapper.entityToResponse(coachEntity2);
        } catch (Exception e) {
            log.error("An error occurred while updating a Coach", e);
            throw ApiErrorException.builder()
                    .error(e.getMessage())
                    .message("Ocurrió un error al actualizar un entrenador")
                    .build();
        }
    }

    // DELETE
    @Transactional
    public void deleteCoach(Long id) {

        if (!coachRepository.existsById(id)) {
            throw new NotFoundApiException("404 Not Found - Coach with ID " + id + " not found",
                    "No se encontro el entrenador con el Id " + id);
        } else {
            try {
                coachRepository.deleteById(id);
                log.info("A Coach was deleted");
            } catch (Exception e) {
                log.error("An error occurred while deleting a Coach", e);
                throw ApiErrorException.builder()
                        .error(e.getMessage())
                        .message("Ocurrió un error al borrar un entrenador")
                        .build();
            }
        }
    }

}
