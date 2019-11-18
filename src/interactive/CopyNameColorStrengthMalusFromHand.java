package interactive;

public class CopyNameColorStrengthMalusFromHand extends Interactive  {
    public int priority = 2;
    public String text = "Copy name, type, strength and malus of any card in your hand";


    @Override
    public String getText(){
        return this.text;
    }

    @Override
    public void askPlayer() {

    }
}
