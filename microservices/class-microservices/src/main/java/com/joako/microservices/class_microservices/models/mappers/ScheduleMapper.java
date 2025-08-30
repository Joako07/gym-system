package com.joako.microservices.class_microservices.models.mappers;

import org.springframework.stereotype.Component;

import com.joako.microservices.class_microservices.models.entities.ClassEntity;
import com.joako.microservices.class_microservices.models.entities.ScheduleEntity;
import com.joako.microservices.class_microservices.models.requests.ScheduleRequest;
import com.joako.microservices.class_microservices.models.responses.ScheduleResponse;

@Component  
public class ScheduleMapper {

    public ScheduleResponse entityToResponse(ScheduleEntity scheduleEntity){
         return new ScheduleResponse(
            scheduleEntity.getId(),
            scheduleEntity.getDay(),
            scheduleEntity.getStartTime(),
            scheduleEntity.getClassId().getId(),
            scheduleEntity.getClassId().getName()
         );  
    }

    public ScheduleEntity requestToEntity(ScheduleRequest request){
        return ScheduleEntity.builder()
        .id(request.id())
        .day(request.day())
        .startTime(request.startTime())
        .classId(ClassEntity.builder()
                 .id(request.classId())
                 .build())
        .build();      
    }
}
