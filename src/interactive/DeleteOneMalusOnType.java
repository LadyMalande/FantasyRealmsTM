package interactive;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import sample.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DeleteOneMalusOnType extends Interactive {

    public int priority = 1;
    public final String text;
    public ArrayList<Type> types;
    private int thiscardid;

    public DeleteOneMalusOnType(int id, ArrayList<Type> types) {
        this.text = "Delete one malus on card of type " + giveListOfTypesWithSeparator(types, " or ");
        this.types = types;
        this.thiscardid = id;
    }
    private static boolean dialogOpen = false;
    public static void askPlayer(BoardController board, int thiscardid, String[] splitted) {
        if (!dialogOpen) {
        List<String> choices = new ArrayList<>();

        for(String s: splitted){
            choices.add(s);
        }
        Platform.runLater(()-> {
            ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
            dialog.setTitle(BigSwitches.switchIdForName(thiscardid));
            dialog.setHeaderText("Choose card on which to remove one malus");
            dialog.setContentText("Chosen card:");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                board.client.sendMessage("DelereOneMalusOnType#" + result.get());
            }
        });
            dialogOpen = false;
        }
    }
}
