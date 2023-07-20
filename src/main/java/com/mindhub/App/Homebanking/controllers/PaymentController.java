package com.mindhub.App.Homebanking.controllers;

import com.mindhub.App.Homebanking.dtos.PaymentDTO;
import com.mindhub.App.Homebanking.models.Account;
import com.mindhub.App.Homebanking.models.Client;
import com.mindhub.App.Homebanking.models.Card;
import com.mindhub.App.Homebanking.models.Transaction;
import com.mindhub.App.Homebanking.models.enums.TransactionType;
import com.mindhub.App.Homebanking.services.AccountService;
import com.mindhub.App.Homebanking.services.CardService;
import com.mindhub.App.Homebanking.services.ClientService;
import com.mindhub.App.Homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.transaction.Transactional;
import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api")
public class PaymentController {
    @Autowired
    ClientService clientService;
    @Autowired
    CardService cardService;
    @Autowired
    TransactionService transactionService;
    @Autowired
    AccountService accountService;
    @Transactional
    @PostMapping("/payments")
    public ResponseEntity<Object> makePayment(@RequestBody PaymentDTO paymentDTO){

        Card card = cardService.findByNumber(paymentDTO.getCardNumber());
        Client client = clientService.findByCardNumber(paymentDTO.getCardNumber());
        Set<Account> accountList = client.getAccounts();
        List<Account> balanceAcc = accountList.stream().filter(account -> account.getBalance() >= paymentDTO.getAmount()).collect(Collectors.toList());
        Account account = balanceAcc.stream().findFirst().orElse(null);

        if(paymentDTO.getCardNumber().isBlank()){
            return new ResponseEntity<>("The number is invalid, try again.", HttpStatus.FORBIDDEN);
        }
        if(paymentDTO.getAmount() <= 0){
            return new ResponseEntity<>("The amount is invalid, try again.", HttpStatus.FORBIDDEN);
        }
        if(paymentDTO.getDescription().isBlank()){
            return new ResponseEntity<>("The description is blank, try again.", HttpStatus.FORBIDDEN);
        }
        if(!paymentDTO.getCardholder().equals(card.getCardholder())){
            return new ResponseEntity<>("The cardholder is invalid, try again.", HttpStatus.FORBIDDEN);
        }
        if(!card.getNumber().equals(paymentDTO.getCardNumber()) ){
            return new ResponseEntity<>("The number is invalid, try again.", HttpStatus.FORBIDDEN);
        }
        if(card.getCvv() != paymentDTO.getCvv()){
            return new ResponseEntity<>("The cvv is invalid, try again.", HttpStatus.FORBIDDEN);
        }

        //PASO LA FECHA RECIBIDA X PARAM A UN LOCAL DATE Y DEBAJO VERIFICO Q NO HAYA VENCIDO
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-yyyy");
        YearMonth paymentDTOThruDate = YearMonth.parse(paymentDTO.getThruDate(), formatter);
        YearMonth cardThruDate = YearMonth.of(card.getThruDate().getYear(), card.getThruDate().getMonth());
        System.out.println(paymentDTOThruDate.equals(cardThruDate));

        if(paymentDTOThruDate.isBefore(YearMonth.now())){
            return new ResponseEntity<>("The card is expired, try again.", HttpStatus.FORBIDDEN);
        }
        if(!cardThruDate.equals(paymentDTOThruDate)){
            return new ResponseEntity<>("The thruDate is invalid, try again.", HttpStatus.FORBIDDEN);
        }
        if(account == null){
            return new ResponseEntity<>("The account is invalid, try again.", HttpStatus.FORBIDDEN);
        }
        if(!card.getActiveCard()){
            return new ResponseEntity<>("The card is inactive, try again.", HttpStatus.FORBIDDEN);
        }
        if(!account.getActiveAcc()){
            return new ResponseEntity<>("The account is inactive, try again.", HttpStatus.FORBIDDEN);
        }

        Transaction transaction = new Transaction(TransactionType.DEBIT, - paymentDTO.getAmount(), paymentDTO.getDescription(), LocalDateTime.now(), account);
        transaction.setBalance(account.getBalance() - paymentDTO.getAmount());
        account.setBalance(account.getBalance() - paymentDTO.getAmount());
        transactionService.save(transaction);
        accountService.save(account);

        return new ResponseEntity<>("Payment created successfully.", HttpStatus.CREATED);
    }
}
