package bonuses;

import sample.BigSwitches;
import sample.BoardController;
import sample.Card;
import sample.Type;

public class PlusForEachSelftypeExceptThis extends Bonus  {
    public int how_much;
    public String text;
    public Type type;
    public int thiscardid;

    public PlusForEachSelftypeExceptThis(int how_much, int thiscardid, Type type) {
        this.how_much = how_much;
        this.type = type;
        this.thiscardid = thiscardid;
        this.text = "+" + Integer.toString(how_much) + " for each other card of type " + BigSwitches.switchTypeForName(type) + " in your hand";
    }

    @Override
    public String getText(){
        return this.text;
    }

    @Override
    public int count() {
        int sum = 0;
        for(Card c: BoardController.player.hand){
            if(c.type.equals(type) && c.id != thiscardid){
                sum += how_much;
            }
        }
        return sum - how_much;
    }
}
