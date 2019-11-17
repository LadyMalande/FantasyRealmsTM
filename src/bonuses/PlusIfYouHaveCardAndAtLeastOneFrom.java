package bonuses;

import java.util.ArrayList;

public class PlusIfYouHaveCardAndAtLeastOneFrom extends Bonus{
    public String text;
    private int how_much;
    private ArrayList<String> namesOfCardsNeeded;
    private String cardNeeded;

    public PlusIfYouHaveCardAndAtLeastOneFrom(int hm, ArrayList<String> cards){
        this.how_much = hm;
        this.namesOfCardsNeeded = cards;
    }

    @Override
    public String getText(){
        return this.text;
    }

    @Override
    public int count() {
        return super.count();
    }
}
