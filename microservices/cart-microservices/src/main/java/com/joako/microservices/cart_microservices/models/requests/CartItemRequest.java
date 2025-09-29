package com.joako.microservices.cart_microservices.models.requests;

public record CartItemRequest(
    Long id,
    Integer productId,
    Integer quantity,
    Long cartId
) {

}
