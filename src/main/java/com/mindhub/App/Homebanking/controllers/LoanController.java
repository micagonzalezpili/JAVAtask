package com.mindhub.App.Homebanking.controllers;

import com.mindhub.App.Homebanking.dtos.LoanApplicationDTO;
import com.mindhub.App.Homebanking.dtos.LoanDTO;
import com.mindhub.App.Homebanking.models.*;
import com.mindhub.App.Homebanking.models.enums.TransactionType;
import com.mindhub.App.Homebanking.repositories.*;
import com.mindhub.App.Homebanking.services.Implement.AccountServiceImplement;
import com.mindhub.App.Homebanking.services.Implement.ClientServiceImplement;
import com.mindhub.App.Homebanking.services.Implement.LoanServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    private ClientServiceImplement clientServiceImplement;
    @Autowired
    private AccountServiceImplement accountServiceImplement;
    @Autowired
    private LoanServiceImplement loanServiceImplement;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ClientLoanRepository clientLoanRepository;
    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> applyLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication){

        Client client = clientServiceImplement.findByEmail(authentication.getName());
        Account accountOfDestin = accountServiceImplement.findByNumber(loanApplicationDTO.getAccountDestin());
        Loan loan = loanServiceImplement.findByName(loanApplicationDTO.getName());

        if(loan == null){
            return new ResponseEntity<>("The loan does not exist.", HttpStatus.FORBIDDEN);
        }

        if(loanApplicationDTO.getPayment() == 0){
            return  new ResponseEntity<>("Please choose a valid amount of payments.", HttpStatus.FORBIDDEN);
        }
        if(loanApplicationDTO.getAmount() > loan.getMaxAmount()){
            return  new ResponseEntity<>("The amount cannot be greater than the available. Please choose a valid amount.", HttpStatus.FORBIDDEN);
        }
        if(accountOfDestin == null){
            return new ResponseEntity<>("The account of destin does not exist.", HttpStatus.FORBIDDEN);
        }
        if(!client.getAccounts().contains(accountOfDestin)){
            return new ResponseEntity<>("The account does not belong to the authenticated client.", HttpStatus.FORBIDDEN);
        }
        if(!loan.getPayments().contains(loanApplicationDTO.getPayment())){
            return new ResponseEntity<>("Please choose a valid number of payments.", HttpStatus.FORBIDDEN);
        }
        if(!loan.getName().equals(loanApplicationDTO.getName())){
            return new ResponseEntity<>("The name of the loan does not exist.", HttpStatus.FORBIDDEN);
        }

        ClientLoan clientLoan = new ClientLoan(loanApplicationDTO.getAmount() * 1.2, loanApplicationDTO.getPayment());
        Transaction transaction = new Transaction(TransactionType.CREDIT, loanApplicationDTO.getAmount(), loanApplicationDTO.getName() + " loan approved", LocalDateTime.now(), accountOfDestin);
        accountOfDestin.setBalance(accountOfDestin.getBalance() + loanApplicationDTO.getAmount());
        client.addClientLoan(clientLoan);
        loan.addClientLoan(clientLoan);
        accountOfDestin.addTransaction(transaction);
        accountServiceImplement.save(accountOfDestin);
        transactionRepository.save(transaction);
        clientLoanRepository.save(clientLoan);

        return new ResponseEntity<>("The loan has been created successfully.", HttpStatus.CREATED);
    }

    @RequestMapping("/loans")
    public List<LoanDTO> getLoans(){
    return loanServiceImplement.getAllLoansDTO();
    }
}
