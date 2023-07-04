package com.mindhub.App.Homebanking.controllers;
import com.mindhub.App.Homebanking.dtos.AccountDTO;
import com.mindhub.App.Homebanking.dtos.ClientDTO;
import com.mindhub.App.Homebanking.models.Account;
import com.mindhub.App.Homebanking.models.Client;
import com.mindhub.App.Homebanking.models.Transaction;
import com.mindhub.App.Homebanking.repositories.AccountRepository;
import com.mindhub.App.Homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")

public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/accounts")
    public List<AccountDTO> getAccountsDTO(){
      return accountRepository
              .findAll()
              .stream()
              .map(account -> new AccountDTO(account))
              .collect(Collectors.toList());
    }

    @RequestMapping("/accounts/{id}")
    public ResponseEntity<Object> getAccountById(@PathVariable Long id, Authentication authentication){
        Account account = accountRepository.findById(id).orElse(null);
        Client client = clientRepository.findByEmail(authentication.getName());
        if(account == null){
            return new ResponseEntity<>("Account does not exist.", HttpStatus.FORBIDDEN);
        }
        if(client.getAccounts().stream().filter(account1 -> account1.equals(account)).collect(Collectors.toList()).isEmpty()){
            return new ResponseEntity<>("The account does not belong to the authenticated user.", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(new AccountDTO(account), HttpStatus.ACCEPTED);
    }
    /*public AccountDTO getAccountsId(@PathVariable Long id){ // ACOMODAR METODO
    return new AccountDTO(accountRepository
            .findById(id)
            .orElse(null));
    }*/
   @RequestMapping(path = "/clients/current/accounts", method = RequestMethod.POST)
    public ResponseEntity<Object> createAccount(Authentication authentication) {

     Client client = clientRepository.findByEmail(authentication.getName());
     String randomNumber;

     do{
         Random random = new Random();
         randomNumber = String.format("VIN-%06d", random.nextInt(999999));

     }while(accountRepository.findByNumber(randomNumber) != null);

     if (client.getAccounts().size() >= 3) {
          return new ResponseEntity<>("The client can´t have more than 3 accounts.", HttpStatus.FORBIDDEN);
      }else {
         Account account = new Account(randomNumber, LocalDate.now(), 0.0, client);
         client.addAccount(account);
         accountRepository.save(account);

         return new ResponseEntity<>(HttpStatus.CREATED);
     }

    }



}
