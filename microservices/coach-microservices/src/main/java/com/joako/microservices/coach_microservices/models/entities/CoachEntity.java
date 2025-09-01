package com.joako.microservices.coach_microservices.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data  
@Builder 
@AllArgsConstructor
@NoArgsConstructor
public class CoachEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    @Column(nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String cellphone;

}
