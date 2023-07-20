package com.mindhub.App.Homebanking.dtos;
import com.mindhub.App.Homebanking.models.Card;
import com.mindhub.App.Homebanking.models.Client;
import com.mindhub.App.Homebanking.models.enums.CardColor;
import com.mindhub.App.Homebanking.models.enums.CardType;
import java.time.LocalDate;

public class CardDTO {
    private long id;
    private String cardholder;
    private String number;
    private short cvv;
    private LocalDate thruDate;
    private LocalDate fromDate;
    private CardType type;
    private CardColor color;
    private Boolean isActive;

    public CardDTO(Card card){
        this.id = card.getId();
        this.cardholder = card.getCardholder();
        this.number = card.getNumber();
        this.cvv = card.getCvv();
        this.thruDate = card.getThruDate();
        this.fromDate = card.getFromDate();
        this.type = card.getType();
        this.color = card.getColor();
        this.isActive = card.getActiveCard();
    }

    public long getId() {
        return id;
    }
    public String getCardholder() {
        return cardholder;
    }
    public String getNumber() {
        return number;
    }
    public short getCvv() {
        return cvv;
    }
    public LocalDate getThruDate() {
        return thruDate;
    }
    public LocalDate getFromDate() {
        return fromDate;
    }
    public CardType getType() {
        return type;
    }
    public CardColor getColor() {
        return color;
    }
    public Boolean getActive() {
        return isActive;
    }
}
