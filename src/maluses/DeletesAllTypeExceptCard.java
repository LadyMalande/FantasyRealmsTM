package maluses;

import sample.BoardController;
import sample.Card;
import sample.Type;

import java.util.ArrayList;

public class DeletesAllTypeExceptCard extends Malus {
    public String text;
    public ArrayList<Type> types;
    public ArrayList<Integer> cards;

    public DeletesAllTypeExceptCard( ArrayList<Type> types, ArrayList<Integer> cards) {
        this.text = "Deletes all "+ giveListOfTypesWithSeparator(types, ", ")+" except " + giveListOfCardsWithSeparator(cards, ", ");
        this.types = types;
        this.cards = cards;
    }

    @Override
    public String getText(){
        return this.text;
    }

    @Override
    public int count() {

        for(Card c: BoardController.player.hand){
            if(types.contains(c.type) && !cards.contains(c.id)){
                BoardController.player.hand.remove(c);
            }
        }
        return 0;
    }
}
