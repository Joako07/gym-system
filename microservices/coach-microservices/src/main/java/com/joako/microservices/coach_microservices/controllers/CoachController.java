package com.joako.microservices.coach_microservices.controllers;

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

import com.joako.microservices.coach_microservices.models.requests.CoachRequest;
import com.joako.microservices.coach_microservices.models.responses.CoachResponse;
import com.joako.microservices.coach_microservices.services.CoachService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/coaches")
@RequiredArgsConstructor
public class CoachController {

    private final CoachService coachService;

    @GetMapping()
    public List<CoachResponse> getAllCoaches() {
        return coachService.getAllCoaches();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoachResponse> getCoachById(@PathVariable Long id) {
        return ResponseEntity.ok(coachService.getCoachById(id));
    }

    @PostMapping()
    public ResponseEntity<CoachResponse> createCoach(@Valid @RequestBody CoachRequest request) {
        return ResponseEntity.ok(coachService.createCoach(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CoachResponse> updateCoach(@PathVariable Long id, @Valid @RequestBody CoachRequest request) {
        return ResponseEntity.ok(coachService.updateCoach(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoach(@PathVariable Long id) {
        coachService.deleteCoach(id);
        return ResponseEntity.ok().build();
    }

}
