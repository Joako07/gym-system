package com.joako.microservices.client_microservices.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joako.microservices.client_microservices.exceptions.ApiErrorException;
import com.joako.microservices.client_microservices.exceptions.NotFoundApiException;
import com.joako.microservices.client_microservices.models.dtos.ClientDto;
import com.joako.microservices.client_microservices.models.entities.ClientEntity;
import com.joako.microservices.client_microservices.models.mappers.ClientMapper;
import com.joako.microservices.client_microservices.repositories.ClientRepository;
import com.joako.microservices.client_microservices.services.interfaces.ClientService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {

 
  private final ClientRepository clientRepository;

  // CREATE
  @Override
  @Transactional
  public ClientDto createClient(ClientDto clientDto) {
    ClientEntity clientEntity = ClientMapper.dtoToEntity(clientDto);

    try {
      clientRepository.save(clientEntity);
      log.info("A Client was created");
      return ClientMapper.entityToDto(clientEntity);
    } catch (Exception e) {
      log.error("An error occurred while creating a new Client", e);
      throw ApiErrorException.builder()
          .error(e.getMessage())
          .message("Ocurrió un error al crear un cliente")
          .build();
    }
  }

  // GETALL
  @Transactional(readOnly = true)
  @Override
  public Page<ClientDto> getAllClient(int pageNumber, int sizePage, String orderBy, String sortDir) {
    Sort.Direction direction = "desc".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
    Pageable pageable = PageRequest.of(pageNumber, sizePage, Sort.by(direction, orderBy));
    return clientRepository.findAll(pageable).map(ClientMapper::entityToDto);
  }

  // GETONE
  @Override
  @Transactional(readOnly = true)
  public ClientDto getById(long id) {
    return clientRepository.findById(id)
        .map(ClientMapper::entityToDto)
        .orElseThrow(() -> new NotFoundApiException("404 Not Found - Client with ID " + id + " not found",
            "No se encontro el cliente con el Id " + id));
  }

  // UPDATE
  @Override
  @Transactional
  public ClientDto updateClient(long id, ClientDto clientDto) {
    ClientEntity clientEntity = clientRepository.findById(id)
        .orElseThrow(() -> new NotFoundApiException("404 Not Found - Client with ID " + id + " not found",
            "No se encontro el cliente con el Id " + id));

    clientEntity.setId(id);
    clientEntity.setName(clientDto.getName());
    clientEntity.setLastName(clientDto.getLastName());
    clientEntity.setCellphone(clientDto.getCellphone());
    clientEntity.setType(clientDto.getType());

    try {
      ClientEntity clientEntity2 = clientRepository.save(clientEntity);
      log.info("A Client was updated");
      return ClientMapper.entityToDto(clientEntity2);
    } catch (Exception e) {
      log.error("An error occurred while updating a Client", e);
      throw ApiErrorException.builder()
          .error(e.getMessage())
          .message("Ocurrió un error al actualizar un cliente")
          .build();
    }
  }

  // DELETE
  @Override
  @Transactional
  public void deletClient(long id) {
    ClientEntity clientEntity = clientRepository.findById(id)
        .orElseThrow(() -> new NotFoundApiException("404 Not Found - Client with ID " + id + " not found",
            "No se encontro el cliente con el Id " + id));
    try {
      clientRepository.delete(clientEntity);
      log.info("A Client was deleted");
    } catch (Exception e) {
      log.info("An error occurred while deleting a Client");
      throw ApiErrorException.builder()
          .error(e.getMessage())
          .message("Ocurrió un error al borrar un cliente")
          .build();
    }
  }
}
