package com.mindhub.App.Homebanking.dtos;
import com.mindhub.App.Homebanking.models.Account;
import java.time.LocalDate;

public class AccountDTO {
    private String number;

    private LocalDate creationDate;

    private double balance;

    public AccountDTO (Account account){
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
    }
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setClient(ClientDTO clientDTO) {
    }
}
