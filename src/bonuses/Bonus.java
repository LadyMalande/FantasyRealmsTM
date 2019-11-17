package bonuses;

import javafx.collections.ArrayChangeListener;
import sample.BigSwitches;
import sample.Type;

import java.io.Serializable;
import java.util.ArrayList;

public class Bonus implements ScoringInterface, Serializable {
    public int priority = 5;
    public String text;
    public String getText(){
        return this.text;
    }
    public int getPriority(){
        return this.priority;
    }

    @Override
    public int count() {
        int score = 0;

        return score;
    }
    public String giveListOfCardsWithSeparator(ArrayList<Integer> cards, String separator) {
        String listcards = "";
        boolean first = true;
        for (Integer c : cards) {
            if (!first) {
                listcards += separator;
            }
            listcards += BigSwitches.switchIdForName(c);
            first = false;
        }
        return listcards;
    }

    public String giveListOfTypesWithSeparator(ArrayList<Type> types, String separator){
        String listtypes = "";
        boolean first = true;
        for(Type type: types){
            if(!first){
                listtypes += separator;
            }
            listtypes += BigSwitches.switchTypeForName(type);
            first = false;
        }
        return listtypes;
    }
}
