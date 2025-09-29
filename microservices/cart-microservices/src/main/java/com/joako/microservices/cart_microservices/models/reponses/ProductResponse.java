package com.joako.microservices.cart_microservices.models.reponses;

public record ProductResponse(
    Integer id,
    String name,
    String description,
    Double price
) {

}
