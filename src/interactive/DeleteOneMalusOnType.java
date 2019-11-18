package interactive;

import sample.Type;

import java.util.ArrayList;

public class DeleteOneMalusOnType extends Interactive {
    public int priority = 1;
    public String text;
    public ArrayList<Type> types;

    public DeleteOneMalusOnType(ArrayList<Type> types) {
        this.text = "Delete one malus on card of type " + giveListOfTypesWithSeparator(types, " or ");
        this.types = types;
    }

    @Override
    public String getText(){
        return this.text;
    }
    @Override
    public int getPriority(){ return this.priority; }
    @Override
    public void askPlayer() {

    }
}
