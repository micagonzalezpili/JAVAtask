package com.mindhub.App.Homebanking.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mindhub.App.Homebanking.models.ClientLoan;
import com.mindhub.App.Homebanking.models.Loan;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LoanDTO {

    private Long id;
       private String name;
    private double maxAmount;

    private List<Integer> payments;
    public LoanDTO(){}

    public LoanDTO(Loan loan) {
        this.id = loan.getId();
        this.name = loan.getName();
        this.maxAmount = loan.getMaxAmount();
        this.payments = loan.getPayments();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }
}
