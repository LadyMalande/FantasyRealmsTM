package sample;

import java.util.ArrayList;


class Deck {
    ArrayList<Card> deck;

    ArrayList<Card> getDeck(){
        return deck;
    }
    Deck(){
        DeckInitializer di = new DeckInitializer();
        di.storeDecktoFile();
        deck = di.loadDeckFromFile();
        System.out.println(deck.isEmpty()) ;
        //deck.add(new Card("Unicorn", 18, Type.CREATURE, null ,null));
        //deck.add(new Card("Magic staff", 1, Type.WEAPON, null, null));
    }
}
