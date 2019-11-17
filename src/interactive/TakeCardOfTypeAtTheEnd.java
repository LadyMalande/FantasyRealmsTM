package interactive;

import sample.Type;

import java.util.ArrayList;

public class TakeCardOfTypeAtTheEnd extends Interactive  {
    public String text;
    public ArrayList<Type> types;

    public TakeCardOfTypeAtTheEnd(ArrayList<Type> types) {
        this.text = "At the end of the game, you can take one card from the table which is of type " + giveListOfTypesWithSeparator(types, " or ") + " as your eighth card";
        this.types = types;
    }

    @Override
    public String getText(){
        return this.text;
    }

    @Override
    public void askPlayer() {

    }
}
