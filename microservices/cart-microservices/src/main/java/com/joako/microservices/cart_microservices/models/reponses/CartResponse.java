package com.joako.microservices.cart_microservices.models.reponses;

import java.util.List;

public record CartResponse(
    Long id,
    Long clientId,
    List<CartItemsResponse> items
) {

}
