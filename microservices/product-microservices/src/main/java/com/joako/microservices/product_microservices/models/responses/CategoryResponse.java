package com.joako.microservices.product_microservices.models.responses;

import java.util.List;

public record CategoryResponse(
    Integer id,
    String name,
    String description,
    List<ProductResponse> products
) {
    
}
