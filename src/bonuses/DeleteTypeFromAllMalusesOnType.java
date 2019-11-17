package bonuses;

import maluses.Malus;
import sample.BigSwitches;
import sample.BoardController;
import sample.Card;
import sample.Type;

public class DeleteTypeFromAllMalusesOnType extends Bonus  {
    public int priority = 1;
    Type deleteThisTypeFromMaluses;
    Type onWhichType;
    public String text;

    public DeleteTypeFromAllMalusesOnType(Type whichType, Type onWhichType){
        this.deleteThisTypeFromMaluses = whichType;
        this.onWhichType = onWhichType;
        text = "Remove word " + BigSwitches.switchTypeForName(whichType) + " on cards of type "+BigSwitches.switchTypeForName(onWhichType);
    }

    @Override
    public String getText(){
        return this.text;
    }

    @Override
    public int count() {
        for(Card c: BoardController.player.hand){
            if(c.type.equals(onWhichType)) {
                if (!c.maluses.isEmpty()) {
                    for (Malus m : c.maluses) {
                        if (m.types.contains(deleteThisTypeFromMaluses)) {
                            m.types.remove(deleteThisTypeFromMaluses);
                        }
                    }
                }
            }

        }
        return 0;
    }
}
