package interactive;

import java.io.Serializable;

public class Interactive implements InteractiveBonusInterface , Serializable {
    public String text;

    public String getText(){
        return this.text;
    }

    @Override
    public void askPlayer() {

    }
}
