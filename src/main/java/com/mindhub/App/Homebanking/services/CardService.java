package com.mindhub.App.Homebanking.services;

import com.mindhub.App.Homebanking.models.Card;

public interface CardService {
    Card findByNumber(String number);
    void save(Card card);
}
