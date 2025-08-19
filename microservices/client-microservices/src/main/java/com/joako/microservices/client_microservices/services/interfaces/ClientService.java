package com.joako.microservices.client_microservices.services.interfaces;

import org.springframework.data.domain.Page;

import com.joako.microservices.client_microservices.models.dtos.ClientDto;


public interface ClientService {

    public ClientDto createClient(ClientDto clientDto);

    public Page<ClientDto> getAllClient(int pageNumber, int sizePage, String orderBy, String sortDir);

    public ClientDto getById(long id);

    public ClientDto updateClient(long id, ClientDto clientDto);

    public void deletClient(long id);

}
