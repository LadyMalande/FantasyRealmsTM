package bonuses;

import sample.BoardController;
import sample.Card;

import java.util.Comparator;

public class PlusForStrengthsInRow extends Bonus  {
    public String text;

    public PlusForStrengthsInRow() {
        this.text = "+10 for 3 strengths, +30 for 4 strengths, +60 for 5 strengths, +100 for 6 strengths or +150 for 7 strenghts in a row";
    }

    @Override
    public String getText(){
        return this.text;
    }

    @Override
    public int count() {
        BoardController.player.hand.sort(Comparator.comparingInt((Card c) -> c.strength));
        int howMuchInARow = 0;
        int laststrength = -100;
        for(Card c:BoardController.player.hand ){
            if(laststrength + 1 == c.strength){
                howMuchInARow++;
            }
            else if(laststrength != c.strength){
                howMuchInARow = 0;
            }
        }
        switch (howMuchInARow){
            case 3:return 10;
            case 4: return 40;
            case 5: return 60;
            case 6: return 100;
            case 7: return 150;
            default: return 0;
        }
    }
}
