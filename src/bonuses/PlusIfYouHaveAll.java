package bonuses;

import sample.BoardController;
import sample.Card;

import java.util.ArrayList;

public class PlusIfYouHaveAll extends Bonus{
    private int how_much;
    private ArrayList<String> namesOfCardsNeeded;

    public PlusIfYouHaveAll(int hm, ArrayList<String> cards){
        this.how_much = hm;
        this.namesOfCardsNeeded = cards;
    }

    @Override
    public int count() {
        int completed = 0;
        for(String name: namesOfCardsNeeded){
            for(Card card: BoardController.player.hand){
                if(name.equals(card.name)){
                    completed++;
                }
            }
        }
        if(namesOfCardsNeeded.size() == completed){
            return how_much;
        } else {
            return 0;
        }
    }
}
