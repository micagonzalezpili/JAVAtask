package com.mindhub.App.Homebanking;

import com.mindhub.App.Homebanking.models.Card;
import com.mindhub.App.Homebanking.repositories.CardRepository;
import com.mindhub.App.Homebanking.services.CardService;
import com.mindhub.App.Homebanking.utilities.CardUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CardUtilsTests {
    @Autowired
    CardRepository cardRepository;
    @Autowired
    CardService cardService;
   @Test
    public void cardNumberIsCreated(){
        String cardNumber = CardUtils.getCardNumber(cardService);
        assertThat(cardNumber,is(not(emptyOrNullString())));
    }

    @Test
    public void getCVVSize(){
        List<Card> cards =  cardRepository.findAll();
        assertThat(cards, hasSize(4));
    }
}
