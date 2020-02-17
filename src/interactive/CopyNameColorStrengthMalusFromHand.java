package interactive;

import javafx.application.Platform;
import javafx.scene.control.ChoiceDialog;
import sample.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CopyNameColorStrengthMalusFromHand extends Interactive  {

    public int priority = 4;
    public final String text = "Copy name, type, strength and malus of any card in your hand";
    private int thiscardid;

    public String getText(){
        return this.text;
    }
    public CopyNameColorStrengthMalusFromHand(int id){
        this.thiscardid = id;
    }

    private static boolean dialogOpen = false;
    public static void askPlayer(BoardController board, int thiscardid) {
        if (!dialogOpen) {
        List<String> choices = new ArrayList<>();
        choices.addAll(board.hand_StackPaneFree.entrySet().stream().filter(set -> !set.getValue().x).map(simcard -> simcard.getValue().y.name).collect(Collectors.toList()));

        Platform.runLater(()-> {
            ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
            dialog.setTitle(BigSwitches.switchIdForName(thiscardid));
            dialog.setHeaderText("Choose card name to copy");
            dialog.setContentText("Chosen name:");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                board.client.sendMessage("CopyCardFromHand#" + result.get());

            }
        });
            dialogOpen = false;
        }
    }
}
