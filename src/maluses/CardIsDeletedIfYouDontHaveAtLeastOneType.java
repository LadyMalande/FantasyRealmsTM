package maluses;

import sample.BigSwitches;
import sample.BoardController;
import sample.Card;
import sample.Type;

public class CardIsDeletedIfYouDontHaveAtLeastOneType extends Malus {
    public String text;
    int thiscardid;
    Type type;

    public CardIsDeletedIfYouDontHaveAtLeastOneType( int thiscardid, Type type) {
        this.text = "This card is deleted if you don't have at least one " + BigSwitches.switchTypeForName(type);
        this.thiscardid = thiscardid;
        this.type = type;
    }

    @Override
    public String getText(){
        return this.text;
    }

    @Override
    public int count() {
        boolean delete = true;
        for(Card c: BoardController.player.hand){
            if(c.type.equals(type)){
                delete = false;
            }
        }
        if(delete){
            BoardController.player.hand.removeIf(x -> x.id == thiscardid);
        }
        return 0;
    }
}
