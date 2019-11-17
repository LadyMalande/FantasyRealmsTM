package maluses;

import sample.BoardController;
import sample.Card;
import sample.Type;

import java.util.ArrayList;

public class DeletesAllExceptTypeOrCard extends Malus {
    public String text;
    public ArrayList<Type> types;
    public ArrayList<Integer> cards;
    private int thiscardid;

    public DeletesAllExceptTypeOrCard( ArrayList<Type> types, ArrayList<Integer> cards, int thiscardid) {
        this.text = "Deletes all cards except " + giveListOfTypesWithSeparator(types, ", ") + " and " + giveListOfCardsWithSeparator(cards, ", ");
        this.types = types;
        this.cards = cards;
        this.thiscardid = thiscardid;
    }

    @Override
    public String getText(){
        return this.text;
    }

    @Override
    public int count() {
        for(Card c: BoardController.player.hand){
            if(!cards.contains(c.id) && !types.contains(c.type) && c.id != thiscardid){
                BoardController.player.hand.remove(c);
            }
        }
        return 0;
    }
}
