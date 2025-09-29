package com.joako.microservices.cart_microservices.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joako.microservices.cart_microservices.models.entities.CartEntity;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long>{

      Optional<CartEntity> findByClientId(long id);

}
