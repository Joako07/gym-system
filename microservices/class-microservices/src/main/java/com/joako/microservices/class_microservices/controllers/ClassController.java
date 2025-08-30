package com.joako.microservices.class_microservices.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joako.microservices.class_microservices.models.requests.ClassRequest;
import com.joako.microservices.class_microservices.models.responses.ClassResponse;
import com.joako.microservices.class_microservices.services.ClassService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/classes")
@RequiredArgsConstructor
public class ClassController {

    
    private final ClassService classServices;

    @GetMapping()
    public ResponseEntity<List<ClassResponse>> getAllClasses() {
        return ResponseEntity.ok(classServices.getAllClasses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassResponse> getClassById(@PathVariable Integer id) {
        return ResponseEntity.ok(classServices.getClassById(id));
    }
    
    @PostMapping()
    public ResponseEntity<Integer> createClass(@Valid @RequestBody ClassRequest request) {
        return ResponseEntity.ok(classServices.createClass(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassResponse> updateClass(@PathVariable Integer id,@Valid @RequestBody ClassRequest request) {
        return ResponseEntity.ok(classServices.updateClass(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletClass(@PathVariable Integer id){
        classServices.deletClass(id);    
        return ResponseEntity.accepted().build();
    }

}
