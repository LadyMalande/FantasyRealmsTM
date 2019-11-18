package bonuses;

import maluses.Malus;
import sample.BigSwitches;
import sample.BoardController;
import sample.Card;
import sample.Type;

public class DeleteSelftypeFromAllMaluses extends Bonus  {
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
    public int count() {
        for(Card c: BoardController.player.hand){
            if(!c.maluses.isEmpty()){
                for(Malus m: c.maluses){
                    m.types.remove(deleteThisTypeFromMaluses);
                }
            }

        }
        return 0;
    }
}
