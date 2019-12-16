package sample;

import bonuses.Bonus;
import interactive.Interactive;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import maluses.Malus;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    public ArrayList<Card> hand;

    Player(ArrayList<Card> your_hand){
        hand = your_hand;

    }

    Integer evaluateHand(){
        HashMap<Type, Malus> types_maluses = new HashMap<>();
        int sum = 0;
        ArrayList<Card> copyDeckToMakeChanges = new ArrayList<>(hand);
        int MAX_PRIORITY = 5;
        int MIN_PRIORITY = 0;
        for(int i = MIN_PRIORITY; i <= MAX_PRIORITY; i++ ){
            for(Card c: copyDeckToMakeChanges){
                if(hand.contains(c)){
                        if(c.interactives != null)
                            for(Interactive in: c.interactives){
                                if(in.getPriority() == i) {
                                    in.askPlayer();
                                    StackPane sp = BoardController.hand_StackPanes.get(copyDeckToMakeChanges.indexOf(c));
                                    sp.getChildren().add(new Label("Not deleted"));
                                }
                            }
                        if(c.bonuses != null)
                            for(Bonus b: c.bonuses){
                                if(b.getPriority() == i) {
                                    sum += b.count(hand);
                                    StackPane sp = BoardController.hand_StackPanes.get(copyDeckToMakeChanges.indexOf(c));
                                    sp.getChildren().add(new Label(Integer.toString(b.count(hand))));
                                }
                            }
                        if(c.maluses != null)
                            for(Malus m: c.maluses){
                                if(m.getPriority() == 3){
                                    types_maluses.put(c.type, m);
                                }
                                else if(m.getPriority() == i) {

                                    sum += m.count(hand);
                                    StackPane sp = BoardController.hand_StackPanes.get(copyDeckToMakeChanges.indexOf(c));
                                    sp.getChildren().add(new Label(Integer.toString(m.count(hand))));
                                }
                            }


                }
            }
            if(i == 3){
                for(Malus m: Sorts.topologicalSort(types_maluses)){
                    sum += m.count(hand);
                }
            }
        }
        for(Card c: hand){
            sum += c.strength;
        }
        return sum;
    }
}
