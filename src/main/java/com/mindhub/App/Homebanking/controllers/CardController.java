package com.mindhub.App.Homebanking.controllers;

import com.mindhub.App.Homebanking.dtos.CardDTO;
import com.mindhub.App.Homebanking.dtos.ClientDTO;
import com.mindhub.App.Homebanking.models.Card;
import com.mindhub.App.Homebanking.models.Client;
import com.mindhub.App.Homebanking.models.enums.CardColor;
import com.mindhub.App.Homebanking.models.enums.CardType;
import com.mindhub.App.Homebanking.services.CardService;
import com.mindhub.App.Homebanking.services.ClientService;
import com.mindhub.App.Homebanking.utilities.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mindhub.App.Homebanking.utilities.CardUtils.getCVV;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private CardService cardServiceImplement;
    @Autowired
    private ClientService clientServiceImplement;

   @GetMapping("/cards/{id}")// VARIABLE DE RUTAAAAAAAA
    public CardDTO  getCardById(@PathVariable Long id) {
        return new CardDTO(cardServiceImplement.getCard(id));
    }


    @PostMapping(path = "/clients/current/cards")
    public ResponseEntity<Object> createCard(@RequestParam CardColor color, @RequestParam CardType type, Authentication authentication)  {

        if (color.name().isBlank()) {
            return new ResponseEntity<>("Card color is blank. Please try again.", HttpStatus.FORBIDDEN);
        }
        if (type.name().isBlank()) {
            return new ResponseEntity<>("Card type is blank. Please try again.", HttpStatus.FORBIDDEN);
        }

        Client currentClient = clientServiceImplement.findByEmail(authentication.getName());
        String cardhold = currentClient.getFirstName() + " " + currentClient.getLastName();

        //if(currentClient.getCards().stream().filter(card -> card.getActiveCard() == true).count() >=3)
        if(!currentClient.getCards()
                .stream()
                .filter(card -> card.getType() == type && card.getActiveCard() == true )
                .filter(card -> card.getColor() == color && card.getActiveCard() == true)
                .collect(Collectors.toList())
                .isEmpty()){
           return new ResponseEntity<>("Client has already card of the same type and same color.", HttpStatus.FORBIDDEN);
       }else{

            Card card = new Card(currentClient, CardUtils.getCardNumber(cardServiceImplement), getCVV(),LocalDate.now().plusYears(5),LocalDate.now(), type, color, true);
            currentClient.addCard(card);
            cardServiceImplement.save(card);

            return new ResponseEntity<>( HttpStatus.CREATED);
        }

    }

    @PatchMapping("/clients/current/cards/{id}")
    public ResponseEntity<Object> deleteCard(@PathVariable Long id, Authentication authentication) {

        Client client = clientServiceImplement.findByEmail(authentication.getName());
        Set<Card> clientCards = client.getCards();
        Card card = cardServiceImplement.getCard(id);

        if (!clientCards.contains(card)) {
            return new ResponseEntity<>("The card does not belong to the authenticated client.", HttpStatus.FORBIDDEN);
        }

        if(card.getActiveCard()){ // VERIFICO SI EL ACTIVE ESTA EN TRUE Y LO PASO A FALSE
            card.setActiveCard(false);
            cardServiceImplement.save(card);
        }

        return new ResponseEntity<>("Your card has been deleted successfully in the view.", HttpStatus.ACCEPTED);
    }


}
