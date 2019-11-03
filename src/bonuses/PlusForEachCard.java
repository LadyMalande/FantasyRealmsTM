package bonuses;

import java.util.ArrayList;

public class PlusForEachCard extends Bonus {
    private int how_much;
    private ArrayList<String> namesOfCardsNeeded;

    public PlusForEachCard(int hm, ArrayList<String> cards){
        this.how_much = hm;
        this.namesOfCardsNeeded = cards;
    }

    @Override
    public int count() {
        return super.count();
    }
}
