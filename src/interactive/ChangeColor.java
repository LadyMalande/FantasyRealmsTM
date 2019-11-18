package interactive;

import sample.Type;

import java.util.ArrayList;

public class ChangeColor extends Interactive {
    public int priority = 2;
    public String text;

    public ChangeColor() {
        this.text = "Change type of one card in your hand";
    }

    @Override
    public String getText(){
        return this.text;
    }

    @Override
    public void askPlayer() {

    }
}
