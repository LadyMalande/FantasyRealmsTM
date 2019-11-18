package sample;

import bonuses.Bonus;
import interactive.Interactive;
import maluses.Malus;

import java.util.ArrayList;

public class Player {
    public ArrayList<Card> hand;
    private static int MAX_PRIORITY = 5;
    private static int MIN_PRIORITY = 1;
     Player(ArrayList<Card> your_hand){
        hand = your_hand;

    }

    Integer evaluateHand(){
        int sum = 0;
        ArrayList<Card> copyDeckToMakeChanges = new ArrayList<>();
        copyDeckToMakeChanges.addAll(hand);
        for(int i = MIN_PRIORITY; i <= MAX_PRIORITY;i++ ){
            for(Card c: copyDeckToMakeChanges){
                if(hand.contains(c)){
                    if(c.giveMinPriority() == i){
                        if(c.interactives != null)
                            for(Interactive in: c.interactives){
                                in.getText();
                            }
                        if(c.bonuses != null)
                            for(Bonus b: c.bonuses){
                                sum += b.count();
                            }
                        if(c.maluses != null)
                            for(Malus m: c.maluses){
                                sum += m.count();
                            }

                    }
                }
            }
        }
        for(Card c: hand){
            sum += c.strength;
        }
        return sum;
    }
}
