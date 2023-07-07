package com.mindhub.App.Homebanking.controllers;
import com.mindhub.App.Homebanking.dtos.ClientDTO;
import com.mindhub.App.Homebanking.models.Account;
import com.mindhub.App.Homebanking.models.Client;
import com.mindhub.App.Homebanking.repositories.AccountRepository;
import com.mindhub.App.Homebanking.repositories.ClientRepository;
import com.mindhub.App.Homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping("/clients")
    public List<ClientDTO> getClientsDTO() {
        return clientService.getAllClientsDTO();
    }

    @RequestMapping("/clients/{id}")// VARIABLE DE RUTAAAAAAAA
    public ClientDTO getClientId(@PathVariable Long id) {
        return clientService.getClientDTO(id);
    }

     @Autowired
     private PasswordEncoder passwordEncoder;

     @RequestMapping(path = "/clients", method = RequestMethod.POST)
     public ResponseEntity<Object> register(@RequestParam String first, @RequestParam String lastName,
          @RequestParam String email, @RequestParam String password) {

         if (first.isBlank()) {
             return new ResponseEntity<>("FirstName is blank. Try again.", HttpStatus.FORBIDDEN);
         }
         if (lastName.isBlank()) {
             return new ResponseEntity<>("LastName is blank. Try again.", HttpStatus.FORBIDDEN);
         }
         if (email.isBlank()) {
             return new ResponseEntity<>("Email is blank. Try again.", HttpStatus.FORBIDDEN);
         }
         if (password.isBlank()) {
             return new ResponseEntity<>("Password is blank. Try again.", HttpStatus.FORBIDDEN);
         }


      if (clientService.findByEmail(email) != null) {
         return new ResponseEntity<>("Email already in use. Please try again.", HttpStatus.FORBIDDEN);
      }

     Client client = new Client(first, lastName, email, passwordEncoder.encode(password));
     clientService.save(client);
     Account account = new Account(createAcc(), LocalDate.now(), 0.0, client);
     accountRepository.save(account);
     client.addAccount(account);

     return new ResponseEntity<>( HttpStatus.CREATED);
     }

     @RequestMapping("/clients/current")
    public ClientDTO getCurrentClient(Authentication authentication){ // info del cliente autenticado
        return new ClientDTO(clientService.findByEmail(authentication.getName()));
     }

     public String createAcc(){
         String randomNumber;
         do{
             Random random = new Random();
             randomNumber = String.format("VIN-%06d", random.nextInt(999999));

         }while(accountRepository.findByNumber(randomNumber) != null);

         return randomNumber;

     }


}