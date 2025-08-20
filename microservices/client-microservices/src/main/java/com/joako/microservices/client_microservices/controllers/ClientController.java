package com.joako.microservices.client_microservices.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.joako.microservices.client_microservices.models.dtos.ClientDto;
import com.joako.microservices.client_microservices.services.interfaces.ClientService;
import com.joako.microservices.client_microservices.utilities.Constants;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getClient(@PathVariable Long id) {
        return new ResponseEntity<>(clientService.getById(id), HttpStatus.OK);
    }
    

    @GetMapping
    public ResponseEntity<Page<ClientDto>> getAllClients(
        @RequestParam(value ="pageNum", defaultValue = Constants.NUMERO_DE_PAGINAS_POR_DEFECTO, required = false)int pageNumber,
        @RequestParam(value ="pageSize", defaultValue = Constants.MEDIDA_DE_PAGINA_POR_DEFECTO, required = false)int sizePage,
        @RequestParam(value = "sortBy", defaultValue = Constants.ORDENAR_POR_DEFECTO, required = false) String orderBy,
        @RequestParam(value = "sortDir",defaultValue = "asc", required = false) String sortDir){
        return new ResponseEntity<>(clientService.getAllClient(pageNumber,sizePage,orderBy,sortDir), HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<ClientDto> creatClient(@Valid @RequestBody ClientDto clientDto) {
        return new ResponseEntity<>(clientService.createClient(clientDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> updateClient(@PathVariable Long id,@Valid @RequestBody ClientDto clientDto) {   
        return new ResponseEntity<>(clientService.updateClient(id, clientDto),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletClient(@PathVariable Long id){
        clientService.deletClient(id);
        return new ResponseEntity<>("Client deleted successfully", HttpStatus.OK);
    }
}
