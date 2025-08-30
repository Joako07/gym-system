package com.joako.microservices.class_microservices.models.responses;

import java.time.LocalTime;

import com.joako.microservices.class_microservices.models.enums.DaysEnum;

public record ScheduleResponse(
    Integer id,
    DaysEnum day,
    LocalTime startTime,
    Integer classId,
    String className
) {

}
