package maluses;

import sample.BoardController;
import sample.Card;
import sample.Type;

import java.util.ArrayList;

public class DeletesAllExceptTypeOrCard extends Malus {
    public String text;
    public ArrayList<Type> types;
    public ArrayList<Type> except;
    public ArrayList<Integer> cards;
    private int thiscardid;

    public DeletesAllExceptTypeOrCard( ArrayList<Type> except, ArrayList<Integer> cards, int thiscardid) {
        this.except = except;
        this.types = getComplementOfTypes(except);
        this.text = "Deletes all cards except " + giveListOfTypesWithSeparator(except, ", ") + " and " + giveListOfCardsWithSeparator(cards, ", ");
        this.cards = cards;
        this.thiscardid = thiscardid;
    }

    private ArrayList<Type> getComplementOfTypes(ArrayList<Type> except) {
        ArrayList<Type> complement_types = new ArrayList<>(){{add(Type.ARMY);add(Type.ARTIFACT); add(Type.WEAPON);add(Type.WEATHER);add(Type.BEAST);add(Type.FLOOD);add(Type.LEADER);add(Type.LAND);add(Type.WIZARD);add(Type.FLAME);add(Type.WILD);}};
       complement_types.add(Type.ARTIFACT);
       complement_types.add(Type.ARMY);
       for(Type t: except){
           complement_types.remove(t);
       }
        return complement_types;
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
                if (!cards.contains(c.id) && !except.contains(c.type) && c.id != thiscardid) {
                    BoardController.player.hand.remove(c);
                }
            }
            return 0;
        }
        else{
            return 0;
        }
    }
    }
