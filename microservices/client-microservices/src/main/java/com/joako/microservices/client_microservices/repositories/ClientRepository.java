package com.joako.microservices.client_microservices.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joako.microservices.client_microservices.models.entities.ClientEntity;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

    @SuppressWarnings("null")
    @EntityGraph(attributePaths = { "classes" }) // Carga la relación "classes" automáticamente
    Page<ClientEntity> findAll(Pageable pageable);
}
