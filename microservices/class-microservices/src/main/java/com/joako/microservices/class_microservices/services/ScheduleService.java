package com.joako.microservices.class_microservices.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joako.microservices.class_microservices.exceptions.ApiErrorException;
import com.joako.microservices.class_microservices.exceptions.NotFoundApiException;
import com.joako.microservices.class_microservices.models.entities.ScheduleEntity;
import com.joako.microservices.class_microservices.models.mappers.ScheduleMapper;
import com.joako.microservices.class_microservices.models.requests.ScheduleRequest;
import com.joako.microservices.class_microservices.models.responses.ScheduleResponse;
import com.joako.microservices.class_microservices.repositories.ScheduleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ClassService classService;
    private final ScheduleMapper scheduleMapper;

     // GETALL
    @Transactional(readOnly = true)
    public List<ScheduleResponse> getAllSchedules() {
        return scheduleRepository.findAll().stream().map(scheduleMapper::entityToResponse).toList();
    }

  // GETONE
    @Transactional(readOnly = true)
    public ScheduleResponse getScheduleById(Integer id) {
        return scheduleRepository.findById(id)
                .map(scheduleMapper::entityToResponse)
                .orElseThrow(() -> new NotFoundApiException("404 Not Found - Schedule with ID " + id + " not found",
                        "No se encontro el horario con el Id " + id));
    }

       // GETALLBYCLASS
    @Transactional(readOnly = true)
    public List<ScheduleResponse> getScheduleByClassId(Integer id) {

        if (id == null) {
            throw new NotFoundApiException("404 Not Found - Class with ID " + id + " not found",
                    "No se encontro la clase con el Id " + id);
        }
        return scheduleRepository.findAll().stream()
                .filter(schedule -> schedule.getClassId().getId().equals(id))
                .map(scheduleMapper::entityToResponse)
                .toList();
    }

     // CREATE
    @Transactional
    public ScheduleResponse createSchedule(ScheduleRequest request) {

        // Vemos que la clase exista
        if (classService.getClassById(request.classId()) == null) {
            throw new NotFoundApiException("404 Not Found - Class with ID " + request.classId() + " not found",
                    "No se encontro la clase con el Id " + request.classId());
        }

        ScheduleEntity scheduleEntity = scheduleMapper.requestToEntity(request);
        try {
            scheduleRepository.save(scheduleEntity);
            log.info("A Schedule was created");
            return scheduleMapper.entityToResponse(scheduleEntity);
        } catch (Exception e) {
            log.error("An error occurred while creating a Schedule", e);
            throw ApiErrorException.builder()
                    .error(e.getMessage())
                    .message("Ocurrió un error al crear un horario")
                    .build();
        }
    }

     // UPDATE
    @Transactional
    public ScheduleResponse updateSchedule(Integer id, ScheduleRequest request) {

        // Vemos que el horario exista
        ScheduleEntity scheduleEntity = scheduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundApiException("404 Not Found - Schedule with ID " + id + " not found",
                        "No se encontro el horario con el Id " + id));

       // Vemos que la clase exista
        if (classService.getClassById(request.classId()) == null) {
            throw new NotFoundApiException("404 Not Found - Class with ID " + request.classId() + " not found",
                    "No se encontro la clase con el Id " + request.classId());
        }

        scheduleEntity.setDay(request.day());
        scheduleEntity.setStartTime(request.startTime());
       
        try {
            ScheduleEntity scheduleEntity2 = scheduleRepository.save(scheduleEntity);
            log.info("A Schedule was updated");
            return scheduleMapper.entityToResponse(scheduleEntity2);
        } catch (Exception e) {
            log.error("An error occurred while updating a Schedule", e);
            throw ApiErrorException.builder()
                    .error(e.getMessage())
                    .message("Ocurrió un error al actualizar un horario")
                    .build();
        }
    }

    // DELETE
    @Transactional
    public void deleteSchedule(Integer id) {

        // Acá uso existById por que no necesito traer el producto entero. Esto es mas liviano
        if (!scheduleRepository.existsById(id)) {
            throw new NotFoundApiException("404 Not Found - Schedule with ID " + id + " not found",
                    "No se encontro el horario con el Id " + id);
        } else {
            try {
                scheduleRepository.deleteById(id);
                log.info("A Schedule was deleted");
            } catch (Exception e) {
                log.error("An error occurred while deleting a Schedule", e);
                throw ApiErrorException.builder()
                        .error(e.getMessage())
                        .message("Ocurrió un error al borrar un horario")
                        .build();
            }
        }
    }
}
