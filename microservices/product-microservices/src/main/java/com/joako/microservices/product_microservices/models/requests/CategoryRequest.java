package com.joako.microservices.product_microservices.models.requests;

import jakarta.validation.constraints.NotNull;

public record CategoryRequest(
    Integer id,
    @NotNull(message = "Category name is required")
    String name,
    String description
) {

} 
