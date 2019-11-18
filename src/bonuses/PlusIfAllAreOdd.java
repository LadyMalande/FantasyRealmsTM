package bonuses;

import sample.BoardController;
import sample.Card;

public class PlusIfAllAreOdd extends Bonus  {
    public String text;
    public int how_much;

    public PlusIfAllAreOdd(int how_much, boolean odd) {
        this.how_much = how_much;
        this.odd = odd;
        String oddoreven="odd";
        if(!odd){
            oddoreven = "even";
        }
        this.text = "+" + how_much + " if all cards in your hand have " + oddoreven + " strength";
    }

    public boolean odd;

    @Override
    public String getText(){
        return this.text;
    }

    @Override
    public int count() {
        int count = 0;
        for(Card c: BoardController.player.hand){
            if(odd){
                if((c.strength % 2) != 0){
                    count++;
                }
            }
            else{
                if((c.strength % 2) == 0){
                    count++;
                }
            }
        }
        if(count == 7){
            return how_much;
        }
        else{
            return 0;
        }
    }
}