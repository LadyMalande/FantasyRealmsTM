package maluses;

import sample.BoardController;
import sample.Card;
import sample.Type;

import java.util.ArrayList;

public class DeletesAllType extends Malus{
    public String text;
    public ArrayList<Type> types;
    public int thiscardid;

    public DeletesAllType( int id, ArrayList<Type> types) {
        this.thiscardid = id;
        this.text = "Deletes all "+ giveListOfTypesWithSeparator(types, ", ");
        this.types = types;
    }

    @Override
    public String getText(){
        return this.text;
    }

    @Override
    public int count(ArrayList<Card> hand) {
        if(!BoardController.player.hand.stream().filter(card -> card.id == this.thiscardid).findAny().isEmpty()) {
            ArrayList<Card> copyDeckToMakeChanges = new ArrayList<>();
            copyDeckToMakeChanges.addAll(BoardController.player.hand);
            for (Card c : copyDeckToMakeChanges) {
                if (types.contains(c.type)) {
                    BoardController.player.hand.remove(c);
                }
            }
            return 0;
        } else{
            return 0;
        }
    }
}
