package client;

import java.util.ArrayList;

public class Player {
    public ArrayList<SimplifiedCard> simhand;

    Player(ArrayList<SimplifiedCard> your_hand){
        simhand = your_hand;
        System.out.println("Player created.");
    }
}
