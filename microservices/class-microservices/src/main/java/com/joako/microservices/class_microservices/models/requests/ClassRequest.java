package com.joako.microservices.class_microservices.models.requests;

import jakarta.validation.constraints.NotBlank;

public record ClassRequest(
    Integer id,
    @NotBlank(message = "Category name is required")
    String name
) {

}
