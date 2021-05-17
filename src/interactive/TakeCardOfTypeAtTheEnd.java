package interactive;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import client.*;

import java.util.*;

/**
 * Class for handling the Necromancer bonus counterpart from the server.
 * @author Tereza Miklóšová
 */
public class TakeCardOfTypeAtTheEnd{

    /**
     * If the same dialog is not open already, it shows a new one.
     */
    private static boolean dialogOpen = false;


    /**
     * Shows a dialog to ask the player how he wants to treat the card in question.
     * @param board Board for communication with the changed cards.
     * @param thiscardid Id of the card that invoked this bonus.
     * @param splitted Strings in the fashion of: "Name" which are cards that can be taken from the table to hand..
     * @param locale Locale in which to show the UI texts.
     */
    public static void askPlayer(BoardController board, int thiscardid, String[] splitted, Locale locale) {
        if (!dialogOpen) {
            ResourceBundle rb = ResourceBundle.getBundle("client.UITexts", locale);


        if(splitted == null){

            Platform.runLater(()-> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(BigSwitches.switchIdForName(thiscardid, locale));

                alert.setHeaderText(rb.getString("interactives_necromancer_alert"));
                alert.setContentText(rb.getString("interactives_necromancer_alertok"));

                board.getClient().sendMessage("TakeCardOfType#");
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

                    SimplifiedCard cardfromTable = Objects.requireNonNull(board.getTableStackPanes().entrySet().stream().filter(set -> set.getValue().y.getName().equals(result.get())).findAny().orElse(null)).getValue().y;
                    int id = cardfromTable.getId();
                    board.getClient().sendMessage("TakeCardOfType#" + id);
                    BoardController.getPlayer().simhand.add(cardfromTable);
                    board.putCardToHand();
                    board.enableSecondActionButtons(false);
                }
                else {
                    // Result is not present - Client pressed Cancel or X
                    board.getClient().sendMessage("TakeCardOfType#" + -1 + "#" );
                }
            });
        }
            dialogOpen = false;
        }
    }
}
