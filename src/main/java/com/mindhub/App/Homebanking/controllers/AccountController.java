package com.mindhub.App.Homebanking.controllers;
import com.mindhub.App.Homebanking.dtos.AccountDTO;
import com.mindhub.App.Homebanking.models.Account;
import com.mindhub.App.Homebanking.models.Client;
import com.mindhub.App.Homebanking.models.Transaction;
import com.mindhub.App.Homebanking.models.enums.AccountType;
import com.mindhub.App.Homebanking.services.AccountService;
import com.mindhub.App.Homebanking.services.ClientService;
import com.mindhub.App.Homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountService accountServiceImplement;
    @Autowired
    private ClientService clientServiceImplement;
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccountsDTO(){
      return accountServiceImplement.getAllAccountsDTO();
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<Object> getAccountById(@PathVariable Long id, Authentication authentication){
        Account account = accountServiceImplement.findById(id);
        Client client = clientServiceImplement.findByEmail(authentication.getName());
        if(account == null){
            return new ResponseEntity<>("Account does not exist.", HttpStatus.FORBIDDEN);
        }
        if(client.getAccounts().stream().filter(account1 -> account1.equals(account)).collect(Collectors.toList()).isEmpty()){
            return new ResponseEntity<>("The account does not belong to the authenticated user.", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(new AccountDTO(account), HttpStatus.ACCEPTED);
    }
    @PostMapping(path = "/clients/current/accounts")
    public ResponseEntity<Object> createAccount(@RequestParam AccountType accountType, Authentication authentication) {

     Client client = clientServiceImplement.findByEmail(authentication.getName());
     String randomNumber;

     do{
         Random random = new Random();
         randomNumber = String.format("VIN-%06d", random.nextInt(999999));

     }while(accountServiceImplement.findByNumber(randomNumber) != null);

     if (client.getAccounts().stream().filter(account -> account.getActiveAcc() == true).count() >= 3) {
          return new ResponseEntity<>("The client can´t have more than 3 accounts.", HttpStatus.FORBIDDEN);
      }else {
         Account account = new Account(randomNumber, LocalDate.now(), 0.0, client, true, accountType);
         client.addAccount(account);
         accountServiceImplement.save(account);

         return new ResponseEntity<>(HttpStatus.CREATED);
     }

    }

    @PatchMapping("/clients/current/accounts")
    public ResponseEntity<Object> deleteAccount(@RequestParam Long id, Authentication authentication){

        Account account = accountServiceImplement.findById(id);
        Client client = clientServiceImplement.findByEmail(authentication.getName());
        Set<Transaction> transactions = account.getTransactions();

        if(!client.getAccounts().contains(account)){
            return new ResponseEntity<>("The account does not belong to the client.", HttpStatus.FORBIDDEN);
        }

        if(account.getBalance() > 0){
            return new ResponseEntity<>("The account cannot be deleted if the balance is greater than $0.", HttpStatus.FORBIDDEN);
        }

        if(transactions.isEmpty()){ // SI LA ACC NO TIENE TRANSACTIONS, LA DESACTIVO Y ACTUALIZO EN LA BD
            account.setActiveAcc(false);
            accountServiceImplement.save(account);
        }

        if(!transactions.isEmpty()){ // SI LA ACC TIENE TRANSACTIONS LAS REMUEVO Y LUEGO DESACTIVO Y ACTUALIZO EN BD
            transactions.forEach(transaction -> transaction.setActive(false));
            transactions.forEach(transaction -> transactionService.save(transaction));
            account.setActiveAcc(false);
            accountServiceImplement.save(account);
        }

        return new ResponseEntity<>("Account deleted in the view.", HttpStatus.ACCEPTED);
    }

}
