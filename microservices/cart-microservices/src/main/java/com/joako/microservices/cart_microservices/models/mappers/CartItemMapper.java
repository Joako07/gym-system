package com.joako.microservices.cart_microservices.models.mappers;

import com.joako.microservices.cart_microservices.models.entities.CartEntity;
import com.joako.microservices.cart_microservices.models.entities.CartItemEntity;
import com.joako.microservices.cart_microservices.models.reponses.CartItemsResponse;
import com.joako.microservices.cart_microservices.models.requests.CartItemRequest;

public class CartItemMapper {

    public CartItemsResponse entityToResponse(CartItemEntity cartItemEntity) {
        return new CartItemsResponse(
                cartItemEntity.getId(),
                cartItemEntity.getProductId(),
                cartItemEntity.getQuantity(),
                cartItemEntity.getCart().getId(),
                cartItemEntity.getCart().getClientId());
    }

    public CartItemEntity requestToEntity(CartItemRequest request) {
        return CartItemEntity.builder()
                .id(request.id())
                .productId(request.productId())
                .quantity(request.quantity())
                .cart(CartEntity.builder()
                        .id(request.cartId())
                        .build())
                .build();
    }
}
