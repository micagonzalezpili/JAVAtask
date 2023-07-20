package com.mindhub.App.Homebanking.utilities;

import com.mindhub.App.Homebanking.services.CardService;

import java.util.Random;

public final class CardUtils {
    public CardUtils() {
    }

    public static String getCardNumber(CardService cardServiceImplement) {
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
    public static short getCVV() {
        Random random = new Random();
        short cvv = (short) random.nextInt(1000);
        return cvv;
    }

}
