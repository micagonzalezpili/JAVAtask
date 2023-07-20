package com.mindhub.App.Homebanking.services;

import com.mindhub.App.Homebanking.dtos.CardDTO;
import com.mindhub.App.Homebanking.models.Card;

public interface CardService {
    Card findByNumber(String number);
    void save(Card card);
    Card getCard(Long id);
    void deleteCard(Card card);
}
