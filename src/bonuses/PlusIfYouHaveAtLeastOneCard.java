package bonuses;

import java.util.ArrayList;

public class PlusIfYouHaveAtLeastOneCard extends Bonus {
    private int how_much;
    private ArrayList<String> namesOfCardsNeeded;

    public PlusIfYouHaveAtLeastOneCard(int hm, ArrayList<String> cards){
        this.how_much = hm;
        this.namesOfCardsNeeded = cards;
    }


    @Override
    public int count() {
        return super.count();
    }
}
