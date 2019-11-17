package maluses;

import sample.BoardController;
import sample.Card;
import sample.Type;

import java.util.ArrayList;

public class MinusIfYouDontHaveAtLeastOneType extends Malus {
    public String text;
    int howMuch;
    ArrayList<Type> types;

    public MinusIfYouDontHaveAtLeastOneType( int howMuch, ArrayList<Type> types) {
        this.text = howMuch + " if you don't have at least one " + giveListOfTypesWithSeparator(types, " or ");
        this.howMuch = howMuch;
        this.types = types;
    }

    @Override
    public String getText(){
        return this.text;
    }

    @Override
    public int count() {

        for(Card c: BoardController.player.hand){
            if(types.contains(c.type)){
                return 0;
            }
        }
        return howMuch;
    }
}
