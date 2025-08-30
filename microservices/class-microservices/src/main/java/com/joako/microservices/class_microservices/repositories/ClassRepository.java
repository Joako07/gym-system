package com.joako.microservices.class_microservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joako.microservices.class_microservices.models.entities.ClassEntity;

@Repository
public interface ClassRepository extends JpaRepository<ClassEntity, Integer> {

}
