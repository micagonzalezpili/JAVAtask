package com.mindhub.App.Homebanking.controllers;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mindhub.App.Homebanking.dtos.ClientDTO;
import com.mindhub.App.Homebanking.models.Client;
import com.mindhub.App.Homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")

public class ClientController {

    @Autowired
    private ClientRepository clientRepository;


    @RequestMapping("/clients")
    public List<ClientDTO> getClients(){
        List<ClientDTO> clients = clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(toList());
        return clients;
    }
    @RequestMapping("/clients/{id}")
    public ClientDTO getClientsId(@PathVariable Long id){
        return clientRepository.findById(id).map(ClientDTO::new).orElse(null);
    }
}
