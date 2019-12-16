package bonuses;

import sample.BigSwitches;
import sample.BoardController;
import sample.Card;
import sample.Type;

import java.util.ArrayList;

public class PlusSumOfStrengthsType extends Bonus  {
    public String text;
    public Type type;

    public PlusSumOfStrengthsType(Type type) {
        this.text = "Plus the sum of all cards of type " + BigSwitches.switchTypeForName(type) + " you have";
        this.type = type;
    }

    @Override
    public String getText(){
        return this.text;
    }

    @Override
    public int count(ArrayList<Card> hand) {
        int total = 0;
        for(Card c: BoardController.player.hand){
            if(type.equals(c.type)){
                total += c.strength;
            }
        }
        return total;
    }
}
