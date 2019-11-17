package maluses;

import sample.BigSwitches;
import sample.BoardController;
import sample.Card;
import sample.Type;

import java.util.ArrayList;

public class MinusForEachOtherSelftypeOrType extends Malus {
    public String text;
    public int howMuch;
    public ArrayList<Type> types;
    public Type selftype;
    public int thiscardid;

    public MinusForEachOtherSelftypeOrType(int howMuch, ArrayList<Type> types, Type selftype, int thiscardid) {
        if(types == null){
            this.text =  howMuch + "for any other " + BigSwitches.switchTypeForName(selftype);
        }else {
            this.text = howMuch + " for each " + giveListOfTypesWithSeparator(types, ", ") + "or any other " + BigSwitches.switchTypeForName(selftype);
        }
        this.howMuch = howMuch;
        this.types = types;
        this.selftype = selftype;
        this.thiscardid = thiscardid;
    }

    @Override
    public String getText(){
        return this.text;
    }

    @Override
    public int count() {
        int total = 0;
        for(Card c: BoardController.player.hand){
            if(types == null && ((selftype.equals(c.type) && c.id != thiscardid))){
                total += howMuch;
            }
            else if(types.contains(c.type) || (selftype.equals(c.type) && c.id != thiscardid)){
                total += howMuch;
            }
        }
        return total;
    }
}
