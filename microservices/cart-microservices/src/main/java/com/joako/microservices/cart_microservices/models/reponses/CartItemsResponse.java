package com.joako.microservices.cart_microservices.models.reponses;

public record CartItemsResponse(
    Long id,
    Integer productId,
    Integer quantity,
    Long cart_id,
    Long clientId
) {

}
