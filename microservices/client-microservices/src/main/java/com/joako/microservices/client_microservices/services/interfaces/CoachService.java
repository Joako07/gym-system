package com.joako.microservices.client_microservices.services.interfaces;

import org.springframework.data.domain.Page;

import com.joako.microservices.client_microservices.models.dtos.CoachDto;

public interface CoachService {

    public CoachDto createCoach(CoachDto coachDto);

    public CoachDto getCoach (long id);

    public Page<CoachDto> getAllCoaches(int pageNumber, int sizePage, String orderBy, String sortDir);

    public CoachDto updateChoach (long id, CoachDto coachDto);

    public void deletCoach(long id);

}
