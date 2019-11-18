package interactive;

import sample.Type;

import java.util.ArrayList;

public class CopyNameAndType extends Interactive {
    public int priority = 2;
    public String text;
    public ArrayList<Type> types;

    public CopyNameAndType( ArrayList<Type> types) {
        this.text = "Copy name and type of any card of these types: " + giveListOfTypesWithSeparator(types, " or ");
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
