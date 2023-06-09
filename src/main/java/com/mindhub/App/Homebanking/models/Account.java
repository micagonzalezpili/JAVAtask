package com.mindhub.App.Homebanking.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Account {
    @Id // le asigno un id a cada cliente y diferenciarlos (primary key)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native") // genera un id automaticamente
    @GenericGenerator(name = "native", strategy = "native")// la strategy la tiene la base de datos genericamente

    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();
    private String number;

    private LocalDate creationDate;

    private double balance;

    public Account(){ }

    public Account(String numberAcc, LocalDate date , Double balanceAcc, Client client){
        number = numberAcc;
        creationDate = date;
        balance = balanceAcc;
        this.client = client;
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
    @JsonIgnore
    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction){
        transaction.setAccount(this);
        transactions.add(transaction);
    }

    public String toString() {
        return number + " " + creationDate + " " + balance ;
    }
}
