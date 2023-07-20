package com.mindhub.App.Homebanking.dtos;
import com.mindhub.App.Homebanking.models.Transaction;
import com.mindhub.App.Homebanking.models.enums.TransactionType;
import java.time.LocalDateTime;

public class TransactionDTO {
    private Long id;
    private TransactionType type;
    private double amount;
    private String description;
    private LocalDateTime date;
    private Boolean isActive;
    private double balance;
    public TransactionDTO(){};
    public TransactionDTO(Transaction transaction){
        this.type = transaction.getType();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.date = transaction.getDate();
        this.id = transaction.getId();
        this.isActive = transaction.getActive();
        this.balance = transaction.getBalance();
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
    public Boolean getActive() {
        return isActive;
    }

    public double getBalance() {
        return balance;
    }
}
