package sample;

import java.util.ArrayList;


class Deck {
    ArrayList<Card> deck;
    Deck(){
        deck = new ArrayList<>();
        deck.add(new Card("Unicorn", 18, Type.CREATURE, null ,null));
        deck.add(new Card("Magic staff", 1, Type.WEAPON, null, null));
    }
}
