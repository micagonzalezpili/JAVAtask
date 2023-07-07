package com.mindhub.App.Homebanking.controllers;

import com.mindhub.App.Homebanking.models.Account;
import com.mindhub.App.Homebanking.models.Client;
import com.mindhub.App.Homebanking.models.Transaction;
import com.mindhub.App.Homebanking.models.enums.TransactionType;
import com.mindhub.App.Homebanking.repositories.AccountRepository;
import com.mindhub.App.Homebanking.repositories.ClientRepository;
import com.mindhub.App.Homebanking.repositories.TransactionRepository;
import com.mindhub.App.Homebanking.services.Implement.AccountServiceImplement;
import com.mindhub.App.Homebanking.services.Implement.ClientServiceImplement;
import com.mindhub.App.Homebanking.services.Implement.TransactionServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    private ClientServiceImplement clientServiceImplement;
    @Autowired
    private AccountServiceImplement accountServiceImplement;
    @Autowired
    private TransactionServiceImplement transactionServiceImplement;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> createTransaction(@RequestParam double amount, @RequestParam String description,
                                                    @RequestParam String originAcc, @RequestParam String destinAcc,
                                                    Authentication authentication){

        Account account1 = accountServiceImplement.findByNumber(originAcc);
        Account account2 = accountServiceImplement.findByNumber(destinAcc);
        Client client = clientServiceImplement.findByEmail(authentication.getName());

        if(amount > account1.getBalance()){
            return new ResponseEntity<>("Cannot transfer more than the available amount.", HttpStatus.FORBIDDEN);
        }
        if(amount == 0.0){
            return new ResponseEntity<>("Please write a valid amount.", HttpStatus.FORBIDDEN);
        }
        if(description.isBlank()){
            return new ResponseEntity<>("Please write a valid description.", HttpStatus.FORBIDDEN);
        }
        if(originAcc.isBlank()){
            return new ResponseEntity<>("Please choose a valid origin account.", HttpStatus.FORBIDDEN);
        }
        if(destinAcc.isBlank()){
            return new ResponseEntity<>("Please choose a valid destin account.", HttpStatus.FORBIDDEN);
        }
        if(account1.equals(account2)){
            return new ResponseEntity<>("Cannot transfer to the same account.", HttpStatus.FORBIDDEN);
        }
        if(account1 == null){
            return new ResponseEntity<>("Nonexistent account.", HttpStatus.FORBIDDEN);
        }
        if(client.getAccounts().stream().filter(acc -> acc.equals(account1)).collect(Collectors.toList()).isEmpty()){
            return new ResponseEntity<>("The account does not belong to the client.", HttpStatus.FORBIDDEN);
        }
        if(account2 == null){
            return new ResponseEntity<>("The account does not exist.", HttpStatus.FORBIDDEN);
        }

        Transaction debitTransaction = new Transaction(TransactionType.DEBIT, - amount, description + " "+ originAcc, LocalDateTime.now(), account1);
        account1.addTransaction(debitTransaction);
        account1.setBalance(account1.getBalance() - amount);
        transactionServiceImplement.save(debitTransaction);
        accountServiceImplement.save(account1);

        Transaction creditTransaction = new Transaction(TransactionType.CREDIT, amount, description + " " + destinAcc, LocalDateTime.now(), account2);
        account2.addTransaction(creditTransaction);
        account2.setBalance(account2.getBalance() + amount);
        transactionServiceImplement.save(creditTransaction);
        accountServiceImplement.save(account2);

        return new ResponseEntity<>("The transaction was made successfully.", HttpStatus.CREATED);

    }
}
