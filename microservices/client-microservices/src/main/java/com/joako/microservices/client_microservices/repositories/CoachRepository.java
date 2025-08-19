package com.joako.microservices.client_microservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joako.microservices.client_microservices.models.entities.CoachEntity;

@Repository
public interface CoachRepository extends JpaRepository<CoachEntity, Long>{

}
