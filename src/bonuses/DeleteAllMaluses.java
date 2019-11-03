package bonuses;

import sample.BoardController;
import sample.Card;

public class DeleteAllMaluses extends Bonus {
    public int priority = 1;
    public String text = "Remove all maluses from all cards";
    @Override
    public int count() {
        for(Card c: BoardController.player.hand){
            c.maluses.clear();
        }
        return 0;
    }
}
