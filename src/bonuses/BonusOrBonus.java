package bonuses;

import java.util.ArrayList;

public class BonusOrBonus extends Bonus {

    String text;
    Bonus b1;
    Bonus b2;

    public BonusOrBonus(Bonus b1, Bonus b2) {
        this.text = b1.getText() + "\n----- OR -----\n" + b2.getText();
        this.b1 = b1;
        this.b2 = b2;
    }



    @Override
    public int count() {
        if(b1.count() > b2.count()){
            return b1.count();
        }
        else{
            return b2.count();
        }
    }

    @Override
    public String getText() {
        return this.text;
    }
}
