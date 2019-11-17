package bonuses;

import sample.BigSwitches;
import sample.BoardController;
import sample.Card;
import sample.Type;

public class PlusIfYouDontHaveType extends Bonus  {
    public String text;
    public int howMuch;
    public Type type;

    public PlusIfYouDontHaveType( int howMuch, Type type) {
        this.text = "+" + howMuch + " if you don't have any " + BigSwitches.switchTypeForName(type);
        this.howMuch = howMuch;
        this.type = type;
    }

    @Override
    public String getText(){
        return this.text;
    }

    @Override
    public int count() {
        for(Card c: BoardController.player.hand){
            if(c.type.equals(type)){
                return 0;
            }
        }
        return howMuch;
    }
}
