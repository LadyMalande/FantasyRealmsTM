package bonuses;

import sample.BigSwitches;
import sample.BoardController;
import sample.Card;
import sample.Type;

public class DeleteAllMalusesOnType extends Bonus  {
    public int priority = 1;
    public String text;
    private Type deleteMalusesOnThisType;

    public DeleteAllMalusesOnType(Type t){
        this.deleteMalusesOnThisType = t;
        this.text = "Remove all maluses from type " + BigSwitches.switchTypeForName(deleteMalusesOnThisType);
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
            if(c.type.equals(deleteMalusesOnThisType)){
                if(c.maluses != null){
                    c.maluses.clear();
                }

            }

        }
        return 0;
    }
}
