package com.mindhub.App.Homebanking.controllers;

import com.mindhub.App.Homebanking.dtos.ClientLoanDTO;
import com.mindhub.App.Homebanking.dtos.LoanApplicationDTO;
import com.mindhub.App.Homebanking.dtos.LoanDTO;
import com.mindhub.App.Homebanking.models.*;
import com.mindhub.App.Homebanking.models.enums.TransactionType;
import com.mindhub.App.Homebanking.repositories.*;
import com.mindhub.App.Homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    private ClientService clientServiceImplement;
    @Autowired
    private AccountService accountServiceImplement;
    @Autowired
    private LoanService loanServiceImplement;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ClientLoanRepository clientLoanRepository;
    @Autowired
    private ClientLoanService clientLoanService;
    @GetMapping("/loans")
    public List<LoanDTO> getLoans(){
        return loanServiceImplement.getAllLoansDTO();
    }
    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> applyLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication){

        Client client = clientServiceImplement.findByEmail(authentication.getName());
        Account accountOfDestin = accountServiceImplement.findByNumber(loanApplicationDTO.getAccountDestin());
        Loan loan = loanServiceImplement.findByName(loanApplicationDTO.getName());
        Double loanAmount = null;

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
        if(loan.getName().equals("Mortgage")){
            loanAmount = loanApplicationDTO.getAmount() * 1.5;
        }
        if(loan.getName().equals("Personal")){
            loanAmount = loanApplicationDTO.getAmount() * 1.8;
        }
        if(loan.getName().equals("Car")){
            loanAmount = loanApplicationDTO.getAmount() * 1.6;
        }

        ClientLoan clientLoan = new ClientLoan(loanAmount, loanApplicationDTO.getPayment());
        Transaction transaction = new Transaction(TransactionType.CREDIT, loanApplicationDTO.getAmount(), loanApplicationDTO.getName() + " loan approved",
                LocalDateTime.now(), accountOfDestin);
        transaction.setBalance(accountOfDestin.getBalance() + loanApplicationDTO.getAmount());
        accountOfDestin.setBalance(accountOfDestin.getBalance() + loanApplicationDTO.getAmount());
        client.addClientLoan(clientLoan);
        loan.addClientLoan(clientLoan);
        accountOfDestin.addTransaction(transaction);
        accountServiceImplement.save(accountOfDestin);
        transactionService.save(transaction);
        clientLoanRepository.save(clientLoan);

        return new ResponseEntity<>("The loan has been created successfully.", HttpStatus.CREATED);
    }

    @PostMapping("/admin/loans")
    public ResponseEntity<Object> createNewLoan(@RequestParam String loanName, @RequestParam double maxAmount, @RequestParam List<Integer> payments,
                                                @RequestParam double percentage, Authentication authentication){
        Client client = clientServiceImplement.findByEmail(authentication.getName());
        List<LoanDTO> existentLoans = loanServiceImplement.getAllLoansDTO();

        if(!client.getEmail().equals("admin@gmail.com")){
            return new ResponseEntity<>("Only admins can create new loans.", HttpStatus.FORBIDDEN);
        }
        if (existentLoans.stream().anyMatch(loan -> loan.getName().equals(loanName))) {
            return new ResponseEntity<>("This loan already exists.", HttpStatus.FORBIDDEN);
        }
        if(loanName.isBlank()){
            return new ResponseEntity<>("Loan name is blank, please try again.", HttpStatus.FORBIDDEN);
        }
        if(maxAmount <= 0){
            return new ResponseEntity<>("Amount invalid, please try again.", HttpStatus.FORBIDDEN);
        }
        if(payments.isEmpty()){
            return new ResponseEntity<>("A list of payments is needed.", HttpStatus.FORBIDDEN);
        }
        if(percentage <= 0){
            return new ResponseEntity<>("Percentage invalid, please try with a valid number.", HttpStatus.FORBIDDEN);
        }

        Loan newLoan = new Loan(loanName, maxAmount, payments, percentage);
        loanServiceImplement.save(newLoan);

        return new ResponseEntity<>("Loan created successfully.", HttpStatus.CREATED);
    }
   @PostMapping("/loans/payment")
   public ResponseEntity<Object> payFee(@RequestParam String originAcc, @RequestParam Long id,
                                         Authentication authentication){

        Account account = accountServiceImplement.findByNumber(originAcc);
        Client client = clientServiceImplement.findByEmail(authentication.getName());
        ClientLoan clientLoan = clientLoanService.findById(id);
        Double feeToPay = clientLoan.getAmount() / clientLoan.getPayments();

        if(originAcc == null ){
            return new ResponseEntity<>("Account invalid. Try again.", HttpStatus.FORBIDDEN);
        }
        if(!client.getAccounts().contains(account)){
            return new ResponseEntity<>("The account don't belong to the client.", HttpStatus.FORBIDDEN);
        }
        if (clientLoan == null){
            return new ResponseEntity<>("The loan is invalid.", HttpStatus.FORBIDDEN);
        }
        if(account.getBalance() < feeToPay){
            return new ResponseEntity<>("Insufficient founds.", HttpStatus.FORBIDDEN);
        }

        Transaction transaction = new Transaction(TransactionType.DEBIT, - feeToPay, "Fee payment.", LocalDateTime.now(), account);
        account.addTransaction(transaction);
        transaction.setBalance(account.getBalance() - feeToPay);
        account.setBalance(account.getBalance() - feeToPay);
        clientLoan.setPayments(clientLoan.getPayments() - 1);
        transactionService.save(transaction);
        accountServiceImplement.save(account);

        return new ResponseEntity<>("Payment done successfully", HttpStatus.CREATED);
    }
}
