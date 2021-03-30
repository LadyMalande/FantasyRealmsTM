package bonuses;

import maluses.Malus;
import sample.BigSwitches;
import sample.BoardController;
import sample.Card;
import sample.Type;

import java.util.ArrayList;

public class DeleteSelftypeFromAllMaluses extends Bonus  {
    public long serialVersionUID = 5;
    public int priority = 1;
    private Type deleteThisTypeFromMaluses;
    public String text;

    public DeleteSelftypeFromAllMaluses(Type t){

        this.deleteThisTypeFromMaluses = t;
        this.text = "Remove word " + BigSwitches.switchTypeForName(deleteThisTypeFromMaluses) + " from all maluses";
    }

    @Override
    public String getText(){
        return this.text;
    }
    @Override
    public int getPriority(){ return this.priority; }
    @Override
    public int count(ArrayList<Card> hand) {
        for(Card c: BoardController.player.hand){
            if(c.maluses != null && !c.maluses.isEmpty()){
                for(Malus m: c.maluses){
                    if(m.types != null && m.types.contains(deleteThisTypeFromMaluses)) {
                        m.types.remove(deleteThisTypeFromMaluses);
                    }
                }
            }

        }
        return 0;
    }
}