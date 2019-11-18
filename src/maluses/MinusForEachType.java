package maluses;

import sample.BoardController;
import sample.Card;
import sample.Type;

import java.util.ArrayList;

public class MinusForEachType extends Malus {
    public int priority = 5;
    public String text;
    public int howMuch;
    public ArrayList<Type> types;

    public MinusForEachType( int howMuch, ArrayList<Type> types) {
        this.text = howMuch + " for each " + giveListOfTypesWithSeparator(types, " or ");
        this.howMuch = howMuch;
        this.types = types;
    }

    @Override
    public String getText(){
        return this.text;
    }
    @Override
    public int getPriority(){ return this.priority; }
    @Override
    public int count() {

        int total = 0;
        for(Card c: BoardController.player.hand){
            if(types.contains(c.type)){
                total += howMuch;
            }
        }
        return total;
    }
}
