package com.mindhub.App.Homebanking.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mindhub.App.Homebanking.models.enums.AccountType;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();
    private String number;
    private LocalDate creationDate;
    private double balance;
    private Boolean activeAcc = true;
    private AccountType accountType;
    public Account(){ }
    public Account(String numberAcc, LocalDate date , Double balanceAcc, Client client, Boolean activeAcc, AccountType accountType){
        number = numberAcc;
        creationDate = date;
        balance = balanceAcc;
        this.client = client;
        this.activeAcc = activeAcc;
        this.accountType = accountType;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
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
    @JsonIgnore
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }

    public Boolean getActiveAcc() {
        return activeAcc;
    }

    public void setActiveAcc(Boolean activeAcc) {
        this.activeAcc = activeAcc;
    }

    @JsonIgnore
    public Set<Transaction> getTransactions() {
        return transactions;
    }
    public void addTransaction(Transaction transaction){
        transaction.setAccount(this);
        transaction.setActive(true);
        transactions.add(transaction);

    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String toString() {
        return number + " " + creationDate + " " + balance ;
    }
}
