package bonuses;

import sample.BigSwitches;
import sample.BoardController;
import sample.Type;

import java.util.List;

public class PlusStrengthOfAnyCardOfType extends Bonus  {
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
        this.text = "Plus the strength of any card of type " + listtypes;
        this.types = types;
    }

    @Override
    public String getText(){
        return this.text;
    }

    @Override
    public int count() {
        return BoardController.deck.maxStrength;
    }
}
