package com.joako.microservices.client_microservices.models.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.joako.microservices.client_microservices.models.dtos.ProductDto;
import com.joako.microservices.client_microservices.models.entities.ProductEntity;

@Component
public class ProductMapper {

     @Autowired
     private static ModelMapper modelMapper;

     private ProductMapper (ModelMapper modelMapper){
        ProductMapper.modelMapper = modelMapper;
     }

     //Convierto de DTO a entidad
     public static ProductEntity dtoToEntity(ProductDto productDto){
      ProductEntity productEntity = modelMapper.map(productDto, ProductEntity.class);
      return productEntity;
     }

     //Convierto de entidad a DTO 
     public static ProductDto entityToDto(ProductEntity productEntity){
        ProductDto productDto = modelMapper.map(productEntity, ProductDto.class);
        return productDto;
     }

}
