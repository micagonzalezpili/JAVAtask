package com.mindhub.App.Homebanking.dtos;
import com.mindhub.App.Homebanking.models.ClientLoan;
public class ClientLoanDTO {
    private Long id;
    private String name;
    private double amount;
    private int payments;
    private Long loanId;

   public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.loanId = clientLoan.getLoan().getId();
        this.name = clientLoan.getLoan().getName();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();

   }
    public String getName() {
        return name;
    }
    public double getAmount() {
        return amount;
    }
    public int getPayments() {
        return payments;
    }

}
