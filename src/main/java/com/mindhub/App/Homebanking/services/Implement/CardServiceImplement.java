package com.mindhub.App.Homebanking.services.Implement;

import com.mindhub.App.Homebanking.dtos.CardDTO;
import com.mindhub.App.Homebanking.models.Card;
import com.mindhub.App.Homebanking.repositories.CardRepository;
import com.mindhub.App.Homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImplement implements CardService {
    @Autowired
    private CardRepository cardRepository;
    @Override
    public Card findByNumber(String number) {
        return cardRepository.findByNumber(number);
    }

    @Override
    public void save(Card card) {
        cardRepository.save(card);
    }

    @Override
    public Card getCard(Long id) {
        return cardRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteCard(Card card) {
        cardRepository.delete(card);
    }
}
