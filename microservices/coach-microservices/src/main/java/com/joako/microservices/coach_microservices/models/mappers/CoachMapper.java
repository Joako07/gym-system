package com.joako.microservices.coach_microservices.models.mappers;

import org.springframework.stereotype.Component;

import com.joako.microservices.coach_microservices.models.entities.CoachEntity;
import com.joako.microservices.coach_microservices.models.requests.CoachRequest;
import com.joako.microservices.coach_microservices.models.responses.CoachResponse;

@Component
public class CoachMapper {

        public CoachEntity requestToEntity(CoachRequest request){
        return CoachEntity.builder()
                .id(request.id())
                .name(request.name())
                .lastName(request.lastName())
                .cellphone(request.cellphone())
                .build();
    }

    public CoachResponse entityToResponse(CoachEntity coachEntity){
        return new CoachResponse(
            coachEntity.getId(),
            coachEntity.getName(),
            coachEntity.getLastName(),
            coachEntity.getCellphone()
        );
    }
}
