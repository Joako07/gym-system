package com.joako.microservices.auth_microservices.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joako.microservices.auth_microservices.user.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>{

     Optional<UserEntity> findByUsername(String username);

}
