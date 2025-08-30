package com.joako.microservices.class_microservices.models.mappers;

import org.springframework.stereotype.Component;

import com.joako.microservices.class_microservices.models.entities.ClassEntity;
import com.joako.microservices.class_microservices.models.requests.ClassRequest;
import com.joako.microservices.class_microservices.models.responses.ClassResponse;
import com.joako.microservices.class_microservices.models.responses.ScheduleResponse;

@Component  
public class ClassMapper {

    public ClassEntity requestToEntity(ClassRequest request){
        return ClassEntity.builder()
        .id(request.id())
        .name(request.name())
        .build();
    }

    public ClassResponse entityToResponse (ClassEntity classEntity){
        return new ClassResponse(
            classEntity.getId(),
            classEntity.getName(),
            classEntity.getSchedules().stream()
            .map(schedule -> new ScheduleResponse(
                schedule.getId(),
                schedule.getDay(),
                schedule.getStartTime(), 
                schedule.getClassId().getId(),
                schedule.getClassId().getName()                
            ))
            .toList()
        );
    }
}
