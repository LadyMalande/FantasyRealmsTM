package interactive;

import javafx.scene.control.ChoiceDialog;
import sample.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CopyNameColorStrengthMalusFromHand extends Interactive  {
    public int priority = 2;
    public String text = "Copy name, type, strength and malus of any card in your hand";
    private int thiscardid;


    @Override
    public String getText(){
        return this.text;
    }
    public CopyNameColorStrengthMalusFromHand(int id){
        this.thiscardid = id;
    }

    @Override
    public void askPlayer() {
        List<String> choices = new ArrayList<>();
        choices.addAll(ArrayListCreator.createListOfNamesFromListOfCards(BoardController.player.hand));

        ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
        dialog.setTitle(BigSwitches.switchIdForName(thiscardid));
        dialog.setHeaderText("Choose card name to copy");
        dialog.setContentText("Chosen name:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            Card thiscard = BoardController.player.hand.stream().filter(card -> card.id == thiscardid).findAny().get();
            Card tocopy = BoardController.player.hand.stream().filter(card -> card.name.equals(result.get())).findAny().get();
            thiscard.name = result.get();
            thiscard.id = tocopy.id;
            thiscard.strength = tocopy.strength;
            thiscard.type = tocopy.type;
            thiscard.maluses = tocopy.maluses;

        }
    }
}
