package maluses;

import bonuses.ScoringInterface;
import sample.BigSwitches;
import sample.Card;
import sample.Type;

import java.io.Serializable;
import java.util.ArrayList;

public class Malus implements ScoringInterface , Serializable {
    public int priority = 3;
    public ArrayList<Type> types;
    public ArrayList<Card> cards;
    public String text;

    public String getText(){
        return this.text;
    }
    public int getPriority(){
        return this.priority;
    }
    @Override
    public int count(){
        return 0;
    }

    String giveListOfCardsWithSeparator(ArrayList<Integer> cards, String separator) {
        StringBuilder listcards = new StringBuilder();
        boolean first = true;
        for (Integer c : cards) {
            if (!first) {
                listcards.append(separator);
            }
            listcards.append(BigSwitches.switchIdForName(c));
            first = false;
        }
        return listcards.toString();
    }

    String giveListOfTypesWithSeparator(ArrayList<Type> types, String separator){
        StringBuilder listtypes = new StringBuilder();
        boolean first = true;
        for(Type type: types){
            if(!first){
                listtypes.append(separator);
            }
            listtypes.append(BigSwitches.switchTypeForName(type));
            first = false;
        }
        return listtypes.toString();
    }
}