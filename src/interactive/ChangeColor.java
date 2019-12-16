package interactive;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import sample.BigSwitches;
import sample.BoardController;
import sample.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChangeColor extends Interactive {
    public int priority = 2;
    public String text;
    public int thiscardid;

    public ChangeColor(int id) {
        this.text = "Change type of one card in your hand";
        this.thiscardid = id;
    }

    @Override
    public String getText(){
        return this.text;
    }

    // All dialogs made with the help of example on https://code.makery.ch/blog/javafx-dialogs-official/

    @Override
    public void askPlayer() {
        //Choose card in hand
        List<String> choices = new ArrayList<>();
        choices.add(BoardController.player.hand.get(0).name);
        choices.add(BoardController.player.hand.get(1).name);
        choices.add(BoardController.player.hand.get(2).name);
        choices.add(BoardController.player.hand.get(3).name);
        choices.add(BoardController.player.hand.get(4).name);
        choices.add(BoardController.player.hand.get(5).name);
        choices.add(BoardController.player.hand.get(6).name);
        if(BoardController.player.hand.size() == 8){
            choices.add(BoardController.player.hand.get(7).name);
        }


        ChoiceDialog<String> dialog1 = new ChoiceDialog<>(choices.get(0), choices);
        dialog1.setTitle(BigSwitches.switchIdForName(thiscardid));
        dialog1.setHeaderText("Choose card");
        dialog1.setContentText("This card will change color:");

        //Choose color
        List<String> choices2 = new ArrayList<>();
        choices2.add(BigSwitches.switchTypeForName(Type.ARMY));
        choices2.add(BigSwitches.switchTypeForName(Type.ARTIFACT));
        choices2.add(BigSwitches.switchTypeForName(Type.CREATURE));
        choices2.add(BigSwitches.switchTypeForName(Type.FLOOD));
        choices2.add(BigSwitches.switchTypeForName(Type.FIRE));
        choices2.add(BigSwitches.switchTypeForName(Type.WEATHER));
        choices2.add(BigSwitches.switchTypeForName(Type.WEAPON));
        choices2.add(BigSwitches.switchTypeForName(Type.LEADER));
        choices2.add(BigSwitches.switchTypeForName(Type.EARTH));
        choices2.add(BigSwitches.switchTypeForName(Type.WIZARD));
        choices2.add(BigSwitches.switchTypeForName(Type.WILD));


        ChoiceDialog<String> dialog2 = new ChoiceDialog<>(choices2.get(0), choices2);
        dialog2.setTitle(BigSwitches.switchIdForName(thiscardid));
        dialog2.setHeaderText("Choose into which type you want to change your selected card");
        dialog2.setContentText("This type will be given to chosen card:");

// Traditional way to get the response value.
        Optional<String> result1 = dialog1.showAndWait();
        Optional<String> result2 = dialog2.showAndWait();
        if (result1.isPresent() && result2.isPresent()){
            BoardController.player.hand.stream().filter(x -> x.name.equals(result1.get())).findFirst().get().type = BigSwitches.switchNameForType(result2.get());
        }


    }
}
