package com.joako.microservices.cart_microservices.models.requests;

public record CartRequest(
    Long id,
    Long clientId
) {

}
