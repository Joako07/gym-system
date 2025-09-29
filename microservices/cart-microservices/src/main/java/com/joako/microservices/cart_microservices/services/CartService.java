package com.joako.microservices.cart_microservices.services;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.joako.microservices.cart_microservices.clients.ClientFeignClient;
import com.joako.microservices.cart_microservices.clients.ProductFeignClient;
import com.joako.microservices.cart_microservices.exceptions.ApiErrorException;
import com.joako.microservices.cart_microservices.exceptions.NotFoundApiException;
import com.joako.microservices.cart_microservices.models.entities.CartEntity;
import com.joako.microservices.cart_microservices.models.entities.CartItemEntity;
import com.joako.microservices.cart_microservices.models.mappers.CartMapper;
import com.joako.microservices.cart_microservices.models.reponses.CartResponse;
import com.joako.microservices.cart_microservices.models.reponses.ClientResponse;
import com.joako.microservices.cart_microservices.models.reponses.ProductResponse;
import com.joako.microservices.cart_microservices.repositories.CartRepository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private CartRepository cartRepository;
    private CartMapper cartMapper;
    private ClientFeignClient clientFeignClient;
    private ProductFeignClient productFeignClient;

    //CREAR CARRO
    @Transactional
    public CartResponse creatCart(long id) {

        ClientResponse client = clientFeignClient.getClientById(id);

        if (client == null) {
            throw new NotFoundApiException("404 Not Found - Client with ID " + id + " not found",
                    "No se encontro el cliente con el Id " + id);
        }

        CartEntity cartEntity = new CartEntity();
        try {
            cartEntity.setClientId(id);
            cartRepository.save(cartEntity);
            log.info("A cart was created");
            return cartMapper.entityToResponse(cartEntity);
        } catch (Exception e) {
            log.error("An error occurred while creating a Cart", e);
            throw ApiErrorException.builder()
                    .error(e.getMessage())
                    .message("Ocurrió un error al crear un carro de compras")
                    .build();
        }

    }

    // AGREGAR PRODUCTO
    @Transactional
    public CartResponse addProduct(Long cartId, Integer productId, int quantity) {

        // Buscar carrito
        CartEntity cartEntity = cartRepository.findById(cartId)
                .orElseThrow(() -> new NotFoundApiException("404 Not Found - cart with ID " + cartId + " not found",
                        "No se encontro el carro con el Id " + cartId));

        // Validar que el producto existe
        ProductResponse product = productFeignClient.getProductById(productId);

        if (product == null) {
            throw new NotFoundApiException("404 Not Found - Product with ID " + productId + " not found",
                    "No se encontro el producto con el Id " + productId);
        }

        // Revisar si el producto ya está en el carrito
        Optional<CartItemEntity> existingItem = cartEntity.getItems().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            // Si ya existe, aumentar la cantidad
            CartItemEntity existingItem2 = existingItem.get();
            existingItem2.setQuantity(existingItem2.getQuantity() + quantity);
        } else {
            // Si no existe, crear un nuevo item
            CartItemEntity newCartItemEntity = new CartItemEntity();
            newCartItemEntity.setProductId(productId);
            newCartItemEntity.setQuantity(quantity);
            newCartItemEntity.setCart(cartEntity);
            // Agrego el item a la lista del carrito
            cartEntity.getItems().add(newCartItemEntity);
        }

        // Guardar carrito actualizado
        try {
            cartRepository.save(cartEntity);
            log.info("An Item was added to the cart");
            return cartMapper.entityToResponse(cartEntity);
        } catch (Exception e) {
            log.error("An error occurred while adding an item to the cart", e);
            throw ApiErrorException.builder()
                    .error(e.getMessage())
                    .message("Se produjo un error al agregar un artículo al carro")
                    .build();
        }

    }

    // ELIMINAR PRODUCTO
    @Transactional
    public CartResponse removeProduct(Long cartId, Integer productId) {

        // Buscar carrito
        CartEntity cartEntity = cartRepository.findById(cartId)
                .orElseThrow(() -> new NotFoundApiException("404 Not Found - cart with ID " + cartId + " not found",
                        "No se encontro el carrito con el Id " + cartId));

        // Validar que el producto existe
        ProductResponse product = productFeignClient.getProductById(productId);

        if (product == null) {
            throw new NotFoundApiException("404 Not Found - Product with ID " + productId + " not found",
                    "No se encontro el producto con el Id " + productId);
        }

        // Revisar si el producto ya está en el carrito
        CartItemEntity itemToRemove = cartEntity.getItems().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new NotFoundApiException(
                        "404 Not Found - the product with ID " + productId + " was not found in the cart",
                        "No se encontro el producto con el Id " + productId + "en el carro"));

        try {
            // Si existe, lo elimino.
            cartEntity.getItems().remove(itemToRemove);
            cartRepository.save(cartEntity);
            return cartMapper.entityToResponse(cartEntity);
        } catch (Exception e) {
            log.error("An error occurred while deleting an item to the cart", e);
            throw ApiErrorException.builder()
                    .error(e.getMessage())
                    .message("Se produjo un error al eliminar un artículo en el carro")
                    .build();
        }

    }

    // ACTUALIZAR CANTIDAD DE PRODUCTO
    @Transactional
    public CartResponse updateProductQuantity(Long cartId, Integer productId, int quantity) {

        // Buscar carrito
        CartEntity cartEntity = cartRepository.findById(cartId)
                .orElseThrow(() -> new NotFoundApiException("404 Not Found - cart with ID " + cartId + " not found",
                        "No se encontro el carro con el Id " + cartId));

        // Validar que el producto existe
         ProductResponse product = productFeignClient.getProductById(productId);

        if (product == null) {
            throw new NotFoundApiException("404 Not Found - Product with ID " + productId + " not found",
                    "No se encontro el producto con el Id " + productId);
        }

        // Revisar si el producto ya está en el carrito
        CartItemEntity itemToUpdate = cartEntity.getItems().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new NotFoundApiException(
                        "404 Not Found - the product with ID " + productId + " was not found in the cart",
                        "No se encontro el producto con el Id " + productId + "en el carro"));

        // 3. Actualizar cantidad o eliminar
        if (quantity <= 0) {
            cartEntity.getItems().remove(itemToUpdate); // gracias a orphanRemoval = true, se borra de la BD
        } else {
            itemToUpdate.setQuantity(quantity);
        }

        // Guardar carrito actualizado
        try {
            cartRepository.save(cartEntity);
            log.info("An Item was added to the cart");
            return cartMapper.entityToResponse(cartEntity);
        } catch (Exception e) {
            log.error("An error occurred while adding an item to the cart", e);
            throw ApiErrorException.builder()
                    .error(e.getMessage())
                    .message("Se produjo un error al agregar un artículo al carro")
                    .build();
        }

    }

    // VACIAR CARRO
    @Transactional
    public void clearCart(Long cartId) {

        // Buscar carrito
        CartEntity cartEntity = cartRepository.findById(cartId)
                .orElseThrow(() -> new NotFoundApiException("404 Not Found - cart with ID " + cartId + " not found",
                        "No se encontro el carro con el Id " + cartId));

        // Vacio la lista de items
        cartEntity.getItems().clear();

        // Guardar carrito actualizado
        try {
            cartRepository.save(cartEntity);
            log.info("An Item was added to the cart");
        } catch (Exception e) {
            log.error("An error occurred while adding an item to the cart", e);
            throw ApiErrorException.builder()
                    .error(e.getMessage())
                    .message("Se produjo un error al agregar un artículo al carro")
                    .build();
        }

    }

    // Obtener el carrito de un cliente
    @Transactional(readOnly = true)
    CartResponse getCartByClientId(long id) {

        // Busco el cliente
        ClientResponse client = clientFeignClient.getClientById(id);

        if (client == null) {
            throw new NotFoundApiException("404 Not Found - Client with ID " + id + " not found",
                    "No se encontro el cliente con el Id " + id);
        }

        // Si el cliente existe busco el carro
        CartEntity cartEntity = cartRepository.findByClientId(id)
                .orElseThrow(() -> new NotFoundApiException(
                        "404 Not Found - No car was found for the client with the Id" + id,
                        "No se encontro el un carro para el cliente con el Id " + id));

        return cartMapper.entityToResponse(cartEntity);
    }

    // Calcular el total del carrito (útil para checkout)
     @Transactional(readOnly = true)
    public BigDecimal calculateTotal(Long cartId) {
       
        // Buscar carrito
        CartEntity cartEntity = cartRepository.findById(cartId)
                .orElseThrow(() -> new NotFoundApiException("404 Not Found - cart with ID " + cartId + " not found",
                        "No se encontro el carro con el Id " + cartId));


        return cartEntity.getItems()
                .stream()
                .map(item -> {
                    //Busco el prodcuto en cada item
                    ProductResponse product = productFeignClient.getProductById(item.getProductId());
                    //Multiplico el precio por la cantidad, convirtiendolos antes en BigDecimal 
                    return BigDecimal.valueOf(product.price()).multiply(BigDecimal.valueOf(item.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
