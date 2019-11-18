package bonuses;

import sample.BoardController;
import sample.Card;

public class PlusForEachOdd extends Bonus  {
    public String text;
    public int how_much;
    private boolean odd;

    public PlusForEachOdd(int how_much, boolean odd) {
        this.odd = odd;
        this.how_much = how_much;
        if(odd) {
            this.text = "+" + how_much + " for each card in your hand with odd strength";
        } else{
            this.text = "+" + how_much + " for each card in your hand with even strength";
        }
    }

    @Override
    public String getText(){
        return this.text;
    }

    @Override
    public int count() {
        int sum = 0;
        for(Card c:BoardController.player.hand){
            if(odd){
                if((c.strength % 2) != 0){
                    sum += how_much;
                }
            }
            else{
                if((c.strength % 2) == 0){
                    sum += how_much;
                }
            }
        }
        return sum;
    }
}
