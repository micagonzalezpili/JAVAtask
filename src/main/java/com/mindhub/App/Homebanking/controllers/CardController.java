package com.mindhub.App.Homebanking.controllers;

import com.mindhub.App.Homebanking.dtos.ClientDTO;
import com.mindhub.App.Homebanking.models.Account;
import com.mindhub.App.Homebanking.models.Card;
import com.mindhub.App.Homebanking.models.Client;
import com.mindhub.App.Homebanking.models.enums.CardColor;
import com.mindhub.App.Homebanking.models.enums.CardType;
import com.mindhub.App.Homebanking.repositories.CardRepository;
import com.mindhub.App.Homebanking.repositories.ClientRepository;
import com.mindhub.App.Homebanking.services.CardService;
import com.mindhub.App.Homebanking.services.ClientService;
import com.mindhub.App.Homebanking.services.Implement.CardServiceImplement;
import com.mindhub.App.Homebanking.services.Implement.ClientServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private CardService cardServiceImplement;
    @Autowired
    private ClientService clientServiceImplement;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping(path = "/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> createCard(@RequestParam CardColor color, @RequestParam CardType type, Authentication authentication)  {

        if (color.name().isBlank()) {
            return new ResponseEntity<>("Card color is blank. Please try again.", HttpStatus.FORBIDDEN);
        }
        if (type.name().isBlank()) {
            return new ResponseEntity<>("Card type is blank. Please try again.", HttpStatus.FORBIDDEN);
        }

        Client currentClient = clientServiceImplement.findByEmail(authentication.getName());
        String cardhold = currentClient.getFirstName() + " " + currentClient.getLastName();


        if(!currentClient.getCards()
                .stream()
                .filter(card -> card.getType() == type )
                .filter(card -> card.getColor() == color)
                .collect(Collectors.toList())
                .isEmpty()){
           return new ResponseEntity<>("Client has already card of the same type and same color.", HttpStatus.FORBIDDEN);
       }else{
            Card card = new Card(currentClient,cardNumber(), createCVV(),LocalDate.now().plusYears(5),LocalDate.now(), type, color);
            currentClient.addCard(card);
            cardServiceImplement.save(card);

            return new ResponseEntity<>( HttpStatus.CREATED);
        }

    }

    private String cardNumber(){
        String randomNumber1;
        String randomNumber2;
        String randomNumber3;
        String randomNumber4;
        String number;
        do{
            Random random = new Random();
            randomNumber1 = String.format("%04d", random.nextInt(9999));
            randomNumber2 = String.format("%04d", random.nextInt(9999));
            randomNumber3 = String.format("%04d", random.nextInt(9999));
            randomNumber4 = String.format("%04d", random.nextInt(9999));
            number = randomNumber1 + " " + randomNumber2  + " "  + randomNumber3 + " "  + randomNumber4;
        }while(cardServiceImplement.findByNumber(number) != null);
        return number;
    }

    public short createCVV() {
        Random random = new Random();
        short cvv = (short) random.nextInt(1000);
        return cvv;
    }

}
