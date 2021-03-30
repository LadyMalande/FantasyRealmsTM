package bonuses;

import sample.BigSwitches;
import sample.BoardController;
import sample.Card;
import sample.Type;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class PlusStrengthOfAnyCardOfType extends Bonus  {
    public long serialVersionUID = 24;
    public String text;
    public List<Type> types;

    public PlusStrengthOfAnyCardOfType(List<Type> types) {
        String listtypes = "";
        boolean first = true;
        for(Type type: types){
            if(!first){
                listtypes +=" or ";
            }
            listtypes += BigSwitches.switchTypeForName(type);
            first = false;
        }
        this.text = "Plus the strength of any card of type " + listtypes + " in your hand";
        this.types = types;
    }

    @Override
    public String getText(){
        return this.text;
    }

    @Override
    public int count(ArrayList<Card> hand) {
        int max_on_hand = 0;
        for(Card c: BoardController.player.hand){
            if(types.contains(c.type) && c.strength>max_on_hand){
                max_on_hand = c.strength;
            }
        }
        return max_on_hand;

    }
}