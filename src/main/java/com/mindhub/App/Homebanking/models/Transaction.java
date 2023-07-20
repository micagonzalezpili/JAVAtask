package com.mindhub.App.Homebanking.models;
import com.mindhub.App.Homebanking.models.enums.TransactionType;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account_id")
    private Account account;
    private TransactionType type;
    private double amount;
    private String description;
    private LocalDateTime date;
    private Boolean isActive = true;
    private double balance;
    public Transaction(){}
    public Transaction(TransactionType type, double amount, String description, LocalDateTime date, Account account){
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.account = account;
        this.isActive = true;
        this.balance = account.getBalance();

    }
  /*  public Transaction(TransactionType type, double amount, String description, LocalDateTime date, Account account, Boolean isActive, double balance){
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.account = account;
        this.isActive = isActive;
        this.balance = balance;
    }*/

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public TransactionType getType() {
        return type;
    }
    public void setType(TransactionType type) {
        this.type = type;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
