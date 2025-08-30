package com.joako.microservices.class_microservices.models.responses;

import java.util.List;

public record ClassResponse(
    Integer id,
    String name,
    List<ScheduleResponse> schedules
) {

}
