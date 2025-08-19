package com.joako.microservices.client_microservices.services.interfaces;

import org.springframework.data.domain.Page;

import com.joako.microservices.client_microservices.models.dtos.ClassDto;

public interface ClassService {

    public ClassDto createClass (ClassDto classDto);

    public Page<ClassDto> getAllClass(int pageNumber, int sizePage, String orderBy, String sortDir);   
    
    public ClassDto getById(long id);

    public ClassDto updateClass(ClassDto classDto, long id);

    public void deletClass(long id);

}
