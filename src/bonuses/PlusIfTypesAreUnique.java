package bonuses;

import sample.BoardController;
import sample.Card;
import sample.Type;

import java.util.ArrayList;

public class PlusIfTypesAreUnique extends Bonus  {
    public String text;
    public int howMuch;

    public PlusIfTypesAreUnique(int howMuch) {
        this.text = "+" + howMuch +" if all types in your hand are unique";
        this.howMuch = howMuch;
    }

    @Override
    public String getText(){
        return this.text;
    }

    @Override
    public int count() {
        ArrayList<Type> types = new ArrayList<Type>();
        for(Card c: BoardController.player.hand){
            if(types.contains(c.type)){
                return 0;
            } else{
                types.add(c.type);
            }
        }

        return howMuch;
    }
}
