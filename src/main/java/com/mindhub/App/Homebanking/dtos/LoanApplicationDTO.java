package com.mindhub.App.Homebanking.dtos;

import com.mindhub.App.Homebanking.models.Account;
import com.mindhub.App.Homebanking.models.Loan;

import java.util.List;

public class LoanApplicationDTO {
    private long id;
    private String name;
    private int payment;
    private double amount;
    private String accountDestin;

    public LoanApplicationDTO(){}
    public LoanApplicationDTO( String name, int payment, double amount,  String accountDestin) {
        this.name = name;
        this.payment = payment;
        this.amount = amount;
        this.accountDestin = accountDestin;
    }

    public long getId() {
        return id;
    }

    public int getPayment() {
        return payment;
    }

    public double getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public String getAccountDestin() {
        return accountDestin;
    }
}
