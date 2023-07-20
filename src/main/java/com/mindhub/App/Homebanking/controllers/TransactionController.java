package com.mindhub.App.Homebanking.controllers;

import com.mindhub.App.Homebanking.dtos.TransactionDTO;
import com.mindhub.App.Homebanking.models.Account;
import com.mindhub.App.Homebanking.models.Client;
import com.mindhub.App.Homebanking.models.Transaction;
import com.mindhub.App.Homebanking.models.enums.TransactionType;
import com.mindhub.App.Homebanking.services.AccountService;
import com.mindhub.App.Homebanking.services.ClientService;
import com.mindhub.App.Homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    private ClientService clientServiceImplement;
    @Autowired
    private AccountService accountServiceImplement;
    @Autowired
    private TransactionService transactionServiceImplement;

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

        Transaction debitTransaction = new Transaction(TransactionType.DEBIT, - amount, description + " "+ destinAcc, LocalDateTime.now(), account1);
        account1.addTransaction(debitTransaction);
        debitTransaction.setBalance(account1.getBalance() - amount);
        account1.setBalance(account1.getBalance() - amount);
        transactionServiceImplement.save(debitTransaction);
        accountServiceImplement.save(account1);

        Transaction creditTransaction = new Transaction(TransactionType.CREDIT, amount, description + " " + originAcc, LocalDateTime.now(), account2);
        account2.addTransaction(creditTransaction);
        creditTransaction.setBalance(account2.getBalance() + amount);
        account2.setBalance(account2.getBalance() + amount);
        transactionServiceImplement.save(creditTransaction);
        accountServiceImplement.save(account2);

        return new ResponseEntity<>("The transaction was made successfully.", HttpStatus.CREATED);

    }
    @PostMapping("/filter/transactions")
    public ResponseEntity<Object> getTransactionsByDate(@RequestParam Long id, @RequestParam String startDate,
                                                        @RequestParam String endDate, Authentication authentication) {

        Client client = clientServiceImplement.findByEmail(authentication.getName());
        Account account = accountServiceImplement.findById(id);
        LocalDateTime start = LocalDateTime.of(LocalDate.parse(startDate), LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(LocalDate.parse(endDate), LocalTime.MAX);

        if(start == null ){
            return new ResponseEntity<>("Start date cannot be null.", HttpStatus.FORBIDDEN);
        }
        if(end == null || end.isAfter(LocalDateTime.now())){
            return new ResponseEntity<>("End date invalid, please try again.", HttpStatus.FORBIDDEN);
        }

        List<Transaction> transactionsList = transactionServiceImplement.getTransactionsByDate(start, end);

       // System.out.println(transactionsList);

        byte[] pdfByte = transactionServiceImplement.generatePDF(account);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "transactions.pdf");

        return new ResponseEntity<>(pdfByte, headers, HttpStatus.OK);
    }
}
