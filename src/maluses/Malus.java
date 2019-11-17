package maluses;

import bonuses.ScoringInterface;
import sample.Card;
import sample.Type;

import java.io.Serializable;
import java.util.ArrayList;

public class Malus implements ScoringInterface , Serializable {
    public int priority = 2;
    public ArrayList<Type> types;
    public ArrayList<Card> cards;
    public String text;

    public String getText(){
        return this.text;
    }

    @Override
    public int count(){
        return 0;
    }
}
