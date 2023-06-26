package com.mindhub.App.Homebanking.controllers;
import com.mindhub.App.Homebanking.dtos.ClientDTO;
import com.mindhub.App.Homebanking.models.Client;
import com.mindhub.App.Homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/clients")
    public List<ClientDTO> getClientsDTO() {
        return clientRepository
                .findAll()
                .stream()
                .map(client -> new ClientDTO(client))
                .collect(toList());
    }

    @RequestMapping("/clients/{id}")// VARIABLE DE RUTAAAAAAAA
    public ClientDTO getClientId(@PathVariable Long id) {
        return new ClientDTO(clientRepository
                .findById(id)
                .orElse(null));
    }

     @Autowired
     private PasswordEncoder passwordEncoder;

     @RequestMapping(path = "/clients", method = RequestMethod.POST)
     public ResponseEntity<Object> register(@RequestParam String first, @RequestParam String lastName,
          @RequestParam String email, @RequestParam String password) {

         if (first.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()) {
             return new ResponseEntity<>("Missing data.", HttpStatus.FORBIDDEN);
         }

      if (clientRepository.findByEmail(email) != null) {
         return new ResponseEntity<>("Email already in use. Please try again.", HttpStatus.FORBIDDEN);
      }

     clientRepository.save(new Client(first, lastName, email, passwordEncoder.encode(password)));
     return new ResponseEntity<>( HttpStatus.CREATED);
     }

     @RequestMapping("/clients/current")
    public ClientDTO getCurrentClient(Authentication authentication){ // info del cliente autenticado
        return new ClientDTO(clientRepository.findByEmail(authentication.getName()));
     }
}