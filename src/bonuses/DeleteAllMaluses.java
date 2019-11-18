package bonuses;

import sample.BoardController;
import sample.Card;

import java.io.Serializable;

public class DeleteAllMaluses extends Bonus implements Serializable {
    public int priority = 1;
    public String text = "Remove all maluses from all cards";

    @Override
    public String getText(){
        return this.text;
    }
    @Override
    public int getPriority(){ return this.priority; }

    @Override
    public int count() {
        for(Card c: BoardController.player.hand){
            if(c.maluses != null) {
                c.maluses = null;
            }
        }
        return 0;
    }
}
