package com.joako.microservices.client_microservices;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValuesConfig {

    @Bean
    ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
