package bonuses;

import sample.BigSwitches;
import sample.BoardController;
import sample.Card;


import java.util.ArrayList;

public class PlusForEachCard extends Bonus {
    private int how_much;
    private ArrayList<Integer> idsOfCardsNeeded;
    public String text;

    public PlusForEachCard(int hm, ArrayList<Integer> ids){
        StringBuilder s = new StringBuilder();
        this.how_much = hm;
        this.idsOfCardsNeeded = ids;
        boolean first = true;
        for(Integer i: idsOfCardsNeeded){
            if(!first){
                s.append(", ");
            }
            s.append(BigSwitches.switchIdForName(i));
            first = false;
        }
        this.text = "+" + how_much + " for each of these cards: " + s;
    }

    @Override
    public String getText(){
        return this.text;
    }

    @Override
    public int count(ArrayList<Card> hand) {
        int sum = 0;
        for(Card c: BoardController.player.hand){
            for(int id: idsOfCardsNeeded) {
                if (c.id == (id)) {
                    sum += how_much;
                }
            }
        }
        return sum;
    }
}
