package interactive;

import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import sample.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DeleteOneMalusOnType extends Interactive {
    public int priority = 1;
    public String text;
    public ArrayList<Type> types;
    private int thiscardid;

    public DeleteOneMalusOnType(int id,ArrayList<Type> types) {
        this.text = "Delete one malus on card of type " + giveListOfTypesWithSeparator(types, " or ");
        this.types = types;
        this.thiscardid = id;
    }

    @Override
    public String getText(){
        return this.text;
    }
    @Override
    public int getPriority(){ return this.priority; }
    @Override
    public void askPlayer() {

        List<String> choices = new ArrayList<>();
        for(Card c: BoardController.player.hand){
            if(types.contains(c.type)){
                if(c.maluses != null) {
                    choices.add(c.name);
                } else if(c.maluses != null && !c.maluses.isEmpty()){
                    choices.add(c.name);
                }
            }
        }


        ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
        dialog.setTitle(BigSwitches.switchIdForName(thiscardid));
        dialog.setHeaderText("Choose card on which to remove one malus");
        dialog.setContentText("Chosen card:");
        Optional<String> result = dialog.showAndWait();
        if(result.isPresent()){
            List<String> choices2 = ArrayListCreator.createListOfTextFromMalus(Objects.requireNonNull(BoardController.player.hand.stream().filter(card -> card.name.equals(result.get())).findAny().orElse(null)));
            if(choices2 == null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(BigSwitches.switchIdForName(thiscardid));
                alert.setHeaderText(null);
                alert.setContentText("No malus to be deleted!");

                alert.showAndWait();
            }else{
                ChoiceDialog<String> dialog2 = new ChoiceDialog<>(choices2.get(0), choices2);
                dialog2.setTitle(BigSwitches.switchIdForName(thiscardid));
                dialog2.setHeaderText("Choose malus to delete");
                dialog2.setContentText("Chosen malus:");
                Optional<String> result2 = dialog2.showAndWait();
                if (result2.isPresent()){
                    BoardController.player.hand.stream().filter(card -> card.name.equals(result.get())).findAny().ifPresent(removeMalusHere -> removeMalusHere.maluses.removeIf(malus -> malus.getText().equals(result2.get())));

                }
            }
        }
    }
}
