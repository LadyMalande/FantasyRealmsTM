package interactive;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import sample.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ChangeColor extends Interactive {

    public int priority = 2;
    public final String text;
    public int thiscardid;

    public ChangeColor(int id) {
        this.text = "Change type of one card in your hand";
        this.thiscardid = id;
    }
    private static boolean dialogOpen = false;
    public static void askPlayer(BoardController board) {
        //Choose card in hand
        if (!dialogOpen) {
        List<String> choices = new ArrayList<>();
        choices.addAll(board.hand_StackPaneFree.entrySet().stream().filter(set -> !set.getValue().x).map(simcard -> simcard.getValue().y.name).collect(Collectors.toList()));


        Platform.runLater(()-> {
        ChoiceDialog<String> dialog1 = new ChoiceDialog<>(choices.get(0), choices);
        dialog1.setTitle(BigSwitches.switchIdForName(31));
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
        dialog2.setTitle(BigSwitches.switchIdForName(31));
        dialog2.setHeaderText("Choose into which type you want to change your selected card");
        dialog2.setContentText("This type will be given to chosen card:");

// Traditional way to get the response value.

            Optional<String> whichCardToChange = dialog1.showAndWait();
            Optional<String> typeForChange = dialog2.showAndWait();
            if (whichCardToChange.isPresent() && typeForChange.isPresent()) {
                board.client.sendMessage("ChangeColor#" + whichCardToChange.get() + "#" + typeForChange.get());
                System.out.println("Sent: " + "ChangeColor#" + whichCardToChange.get() + "#" + typeForChange.get());
            }
        });
        dialogOpen = false;
    }

    }
}
