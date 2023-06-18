package com.mindhub.App.Homebanking.controllers;
import com.mindhub.App.Homebanking.dtos.AccountDTO;
import com.mindhub.App.Homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")

public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping("/accounts")
    public List<AccountDTO> getAccountsDTO(){
      return accountRepository
              .findAll()
              .stream()
              .map(account -> new AccountDTO(account))
              .collect(Collectors.toList());
    }

    @RequestMapping("/accounts/{id}")
    public AccountDTO getAccountsId(@PathVariable Long id){
    return new AccountDTO(accountRepository
            .findById(id)
            .orElse(null));
    }

}
