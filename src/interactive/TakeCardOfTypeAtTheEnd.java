package interactive;

import javafx.scene.control.ChoiceDialog;
import sample.*;

import java.util.ArrayList;
import java.util.Optional;

public class TakeCardOfTypeAtTheEnd extends Interactive  {
    public int priority = 0;
    public String text;
    public ArrayList<Type> types;
    private int thiscardid;

    public TakeCardOfTypeAtTheEnd(int id,ArrayList<Type> types) {
        this.thiscardid = id;
        this.text = "At the end of the game, you can take one card from the table which is of type " + giveListOfTypesWithSeparator(types, " or ") + " as your eighth card";
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
        ArrayList<Card> rightTypes = new ArrayList<>();
        for(Card card: BoardController.cardsOnTable){
            if(types.contains(card.type)){
                rightTypes.add(card);
            }
        }
        ArrayList<String> choices = new ArrayList<>(ArrayListCreator.createListOfNamesFromListOfCards(rightTypes));

        ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
        dialog.setTitle(BigSwitches.switchIdForName(thiscardid));
        dialog.setHeaderText("Choose card name to get from table");
        dialog.setContentText("Chosen name:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            if(BoardController.cardsOnTable.stream().anyMatch(card -> card.name.equals(result.get()))) {
                Card tocopy = BoardController.cardsOnTable.stream().filter(card -> card.name.equals(result.get())).findAny().orElse(null);
                BoardController.player.hand.add(tocopy);
                BoardController.cardsOnTable.remove(tocopy);
            }

        }
    }
}
