package com.joako.microservices.class_microservices.models.requests;

import java.time.LocalTime;

import com.joako.microservices.class_microservices.models.enums.DaysEnum;

import jakarta.validation.constraints.NotNull;

public record ScheduleRequest(

    Integer id,
    @NotNull(message = "Days cannot be null")
    DaysEnum day, 
    @NotNull(message = "The time cannot be null")
    LocalTime startTime, 
    Integer classId
) {

}
