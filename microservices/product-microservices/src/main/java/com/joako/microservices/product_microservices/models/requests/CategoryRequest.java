package com.joako.microservices.product_microservices.models.requests;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(
    Integer id,
    @NotBlank(message = "Category name is required")
    String name,
    String description
) {

} 
