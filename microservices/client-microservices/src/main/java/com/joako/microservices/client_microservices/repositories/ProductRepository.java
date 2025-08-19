package com.joako.microservices.client_microservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joako.microservices.client_microservices.models.entities.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository <ProductEntity, Long>{

}
