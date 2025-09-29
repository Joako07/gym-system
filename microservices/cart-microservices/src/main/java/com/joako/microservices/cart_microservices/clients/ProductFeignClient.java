package com.joako.microservices.cart_microservices.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.joako.microservices.cart_microservices.models.reponses.ProductResponse;

@FeignClient(name="client-microservice", url = "http://localhost:8092/api/v1/products")
public interface ProductFeignClient {

       @GetMapping("/{id}")
       ProductResponse getProductById(@PathVariable("id") Integer id);
}
