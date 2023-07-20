package com.mindhub.App.Homebanking.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PaymentDTO {
    private String cardholder;
    private String cardNumber;
    private int cvv;
    private double amount;
    private String description;
    private String thruDate;

    public PaymentDTO(){}
    public PaymentDTO(String cardholder, String cardNumber, int cvv, double amount, String description, String thruDate) {
        this.cardholder = cardholder + " " + cardholder;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.amount = amount;
        this.description = description;
        this.thruDate = thruDate;
    }

    public String getCardholder() {
        return cardholder;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public int getCvv() {
        return cvv;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getThruDate() {
        return thruDate;
    }
}
