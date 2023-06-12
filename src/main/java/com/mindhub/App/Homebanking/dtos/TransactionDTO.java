package com.mindhub.App.Homebanking.dtos;

import com.mindhub.App.Homebanking.models.Account;
import com.mindhub.App.Homebanking.models.Transaction;
import com.mindhub.App.Homebanking.models.enums.TransactionType;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class TransactionDTO {
    private Long id;
    private TransactionType type;

    private double amount;

    private String description;

    private LocalDateTime date;

    public TransactionDTO(Transaction transaction){
        this.type = transaction.getType();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.date = transaction.getDate();
        this.id = transaction.getId();

    }

    public Long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setAccount(AccountDTO accountDTO){

    }
}
