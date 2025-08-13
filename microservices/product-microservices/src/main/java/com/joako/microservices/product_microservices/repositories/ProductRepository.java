package com.joako.microservices.product_microservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joako.microservices.product_microservices.models.entities.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer>{

}
