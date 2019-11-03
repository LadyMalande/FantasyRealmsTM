package maluses;

import bonuses.ScoringInterface;
import sample.Card;
import sample.Type;

import java.util.ArrayList;

public class Malus implements ScoringInterface {
    public int priority = 2;
    public ArrayList<Type> types;
    public ArrayList<Card> cards;
    @Override
    public int count(){
        return 0;
    }
}
