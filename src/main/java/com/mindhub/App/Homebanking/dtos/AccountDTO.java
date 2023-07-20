package com.mindhub.App.Homebanking.dtos;
import com.mindhub.App.Homebanking.models.Account;
import com.mindhub.App.Homebanking.models.enums.AccountType;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {
    private long id;
    private String number;
    private LocalDate creationDate;
    private double balance;
    private Set<TransactionDTO> transactions;
    private Boolean isActive;
    private AccountType accountType;
    public AccountDTO (Account account){
        this.id = account.getId();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.transactions = account.getTransactions()
                .stream()
                .map(transaction -> new TransactionDTO(transaction))
                .collect(Collectors.toSet());
        this.isActive = account.getActiveAcc();
        this.accountType = account.getAccountType();
    }
    public String getNumber() {
        return number;
    }
    public LocalDate getCreationDate() {
        return creationDate;
    }
    public double getBalance() {
        return balance;
    }
    public long getId() {
        return id;
    }
    public void setClient(ClientDTO clientDTO) {
    }
    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }
    public Boolean getActive() {
        return isActive;
    }

    public AccountType getAccountType() {
        return accountType;
    }
}
