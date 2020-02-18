package interactive;

import javafx.application.Platform;
import javafx.scene.control.ChoiceDialog;
import sample.*;

import java.util.ArrayList;
import java.util.Optional;

public class TakeCardOfTypeAtTheEnd extends Interactive  {
    public int priority = 0;
    public final String text;
    public ArrayList<Type> types;
    private int thiscardid;

    public TakeCardOfTypeAtTheEnd(int id,ArrayList<Type> types) {
        this.thiscardid = id;
        this.text = "At the end of the game, you can take one card from the table which is of type " + giveListOfTypesWithSeparator(types, " or ") + " as your eighth card";
        this.types = types;
    }
    private static boolean dialogOpen = false;
    public static void askPlayer(BoardController board, int thiscardid, String[] splitted) {
        if (!dialogOpen) {
        ArrayList<String> choices = new ArrayList<>();
        for(String s: splitted){
            choices.add(s);
        }
        Platform.runLater(()-> {
            ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
            dialog.setTitle(BigSwitches.switchIdForName(thiscardid));
            dialog.setHeaderText("Choose card name to get from table");
            dialog.setContentText("Chosen name:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                board.client.sendMessage("TakeCardOfType#" + result.get());
                System.out.println("Sent: " + "TakeCardOfType#" + result.get());
            }
        });
            dialogOpen = false;
        }
    }
}
