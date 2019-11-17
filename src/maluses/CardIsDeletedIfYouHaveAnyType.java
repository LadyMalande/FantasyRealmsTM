package maluses;

import sample.BigSwitches;
import sample.BoardController;
import sample.Card;
import sample.Type;

public class CardIsDeletedIfYouHaveAnyType extends Malus {
    public String text;
    private int thiscardid;
    private Type type;

    public CardIsDeletedIfYouHaveAnyType(int thiscardid, Type type) {
        this.text = "This card is deleted if you have any " + BigSwitches.switchTypeForName(type);
        this.thiscardid = thiscardid;
        this.type = type;
    }

    @Override
    public String getText(){
        return this.text;
    }

    @Override
    public int count() {
        boolean delete = false;
        for(Card c: BoardController.player.hand){
            if(c.type.equals(type)){
                delete = true;
                break;
            }
        }
        if(delete){
            BoardController.player.hand.removeIf(x -> x.id == thiscardid);
        }
        return 0;
    }
}
