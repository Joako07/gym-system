package com.joako.microservices.coach_microservices.models.requests;

import jakarta.validation.constraints.NotBlank;

public record CoachRequest(

    Long id,
    @NotBlank(message = "Name is required")
    String name,
    @NotBlank(message = "Lastname is required")
    String lastName,
    String cellphone

) {

}
