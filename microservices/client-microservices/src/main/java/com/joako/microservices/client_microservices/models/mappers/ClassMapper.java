package com.joako.microservices.client_microservices.models.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.joako.microservices.client_microservices.models.dtos.ClassDto;
import com.joako.microservices.client_microservices.models.entities.ClassEntity;

@Component
public class ClassMapper {

    @Autowired
    private static ModelMapper modelMapper;

    private ClassMapper(ModelMapper modelMapper){
        ClassMapper.modelMapper= modelMapper;
    }

    //Convertir de DTO a Entidad 
    public static ClassEntity dtoToEntity(ClassDto classDto){
        ClassEntity classEntity = modelMapper.map(classDto, ClassEntity.class);
        return classEntity;
    }
    
 
   public static ClassDto entityToDto(ClassEntity classEntity){
       return ClassDto.builder()
        .id(classEntity.getId())
        .activity(classEntity.getActivity())
        .days(classEntity.getDays())
        .startClass(classEntity.getStartClass())
        .endTime(classEntity.getEndTime())
        .build();
   }
}
