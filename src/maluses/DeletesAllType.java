package maluses;

import sample.BoardController;
import sample.Card;
import sample.Type;

import java.util.ArrayList;

public class DeletesAllType extends Malus{
    public String text;
    public ArrayList<Type> types;

    public DeletesAllType( ArrayList<Type> types) {
        this.text = "Deletes all "+ giveListOfTypesWithSeparator(types, ", ");
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
                BoardController.player.hand.remove(c);
            }
        }
        return 0;
    }
}
