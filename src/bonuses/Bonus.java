package bonuses;

import java.io.Serializable;

public class Bonus implements ScoringInterface, Serializable {
    public int priority = 5;
    public String text;
    public String getText(){
        return this.text;
    }

    @Override
    public int count() {
        int score = 0;

        return score;
    }
}
