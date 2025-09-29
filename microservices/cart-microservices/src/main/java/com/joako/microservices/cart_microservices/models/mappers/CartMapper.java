package com.joako.microservices.cart_microservices.models.mappers;

import com.joako.microservices.cart_microservices.models.entities.CartEntity;
import com.joako.microservices.cart_microservices.models.reponses.CartItemsResponse;
import com.joako.microservices.cart_microservices.models.reponses.CartResponse;
import com.joako.microservices.cart_microservices.models.requests.CartRequest;

public class CartMapper {

    public CartEntity requestToEntity(CartRequest request) {
        return CartEntity.builder()
                .id(request.id())
                .clientId(request.clientId())
                .build();
    }

    public CartResponse entityToResponse(CartEntity cartEntity) {
        return new CartResponse(
                cartEntity.getId(),
                cartEntity.getClientId(),
                cartEntity.getItems().stream()
                        .map(item -> new CartItemsResponse(
                                item.getId(),
                                item.getProductId(),
                                item.getQuantity(),
                                item.getCart().getId(),
                                item.getCart().getClientId()))
                        .toList());
    }

}
