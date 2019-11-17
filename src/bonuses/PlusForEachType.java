package bonuses;

import sample.BigSwitches;
import sample.BoardController;
import sample.Card;
import sample.Type;

import java.util.ArrayList;

public class PlusForEachType extends Bonus  {
    public String text;
    public ArrayList<Type> types;
    public int how_much;

    public PlusForEachType(String text, ArrayList<Type> types, int how_much) {
        this.types = types;
        this.how_much = how_much;
        String s = new String();
        boolean first = true;
        for(Type t: types){
            if(!first){
                s+=" or ";
            }
            s += BigSwitches.switchTypeForName(t);
            first = false;
        }
        this.text = "+" + Integer.toString(how_much) + " for each card of type " + s;
    }

    @Override
    public String getText(){
        return this.text;
    }

    @Override
    public int count() {
        int sum = 0;
        for(Card c: BoardController.player.hand){
            for(Type type: types) {
                if (c.type.equals(type)) {
                    sum += how_much;
                }
            }
        }

        return sum;
    }
}
