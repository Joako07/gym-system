package com.joako.microservices.cart_microservices.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.joako.microservices.cart_microservices.models.reponses.ClientResponse;

@FeignClient(name="client-microservice", url = "http://localhost:8091/api/v1/client")
public interface ClientFeignClient {

    @GetMapping("/{id}")
    ClientResponse getClientById(@PathVariable("id") long id);

}
