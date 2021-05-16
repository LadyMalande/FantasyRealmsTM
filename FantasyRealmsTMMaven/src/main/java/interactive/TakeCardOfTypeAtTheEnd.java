package interactive;

import client.BigSwitches;
import client.BoardController;
import client.SimplifiedCard;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;

import java.util.*;

public class TakeCardOfTypeAtTheEnd extends Interactive  {

    private static boolean dialogOpen = false;
    public static void askPlayer(BoardController board, int thiscardid, String[] splitted, Locale locale) {
        if (!dialogOpen) {
            ResourceBundle rb = ResourceBundle.getBundle("client.UITexts", locale);


        if(splitted == null){

            Platform.runLater(()-> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(BigSwitches.switchIdForName(thiscardid, locale));

                alert.setHeaderText(rb.getString("interactives_necromancer_alert"));
                alert.setContentText(rb.getString("interactives_necromancer_alertok"));

                board.client.sendMessage("TakeCardOfType#");
            });
        } else{
            ArrayList<String> choices = new ArrayList<>(Arrays.asList(splitted));
            Platform.runLater(()-> {
                ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
                dialog.setTitle(BigSwitches.switchIdForName(thiscardid, locale));
                dialog.setHeaderText(rb.getString("interactives_necromancer"));
                dialog.setContentText(rb.getString("interactives_chosenname"));

                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {

                    SimplifiedCard cardfromTable = Objects.requireNonNull(board.table_StackPaneFree.entrySet().stream().filter(set -> set.getValue().y.name.equals(result.get())).findAny().orElse(null)).getValue().y;
                    int id = cardfromTable.id;
                    board.client.sendMessage("TakeCardOfType#" + id);
                    board.getPlayer().simhand.add(cardfromTable);
                    board.putCardToHand();
                    board.enableSecondActionButtons(false);
                }
                else {
                    // Result is not present - Client pressed Cancel or X
                    board.client.sendMessage("TakeCardOfType#" + -1 + "#" );
                }
            });
        }
            dialogOpen = false;
        }
    }
}
