package interactive;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.ImageView;
import sample.*;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

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
    public static void askPlayer(BoardController board, int thiscardid, String[] splitted, Locale locale) {
        if (!dialogOpen) {
        ArrayList<String> choices = new ArrayList<>();
        ResourceBundle rb = ResourceBundle.getBundle("sample.UITexts", locale);


        if(splitted == null){

            Platform.runLater(()-> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(BigSwitches.switchIdForName(thiscardid, locale));

                alert.setHeaderText(rb.getString("interactives_necromancer_alert"));
                alert.setContentText(rb.getString("interactives_necromancer_alertok"));

                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.OK){
                    board.client.sendMessage("TakeCardOfType#");
                    System.out.println("Sent: " + "TakeCardOfType#");
                } else{
                    board.client.sendMessage("TakeCardOfType#");
                    System.out.println("Sent: " + "TakeCardOfType#");
                }
            });
            dialogOpen = false;
        } else{
            for(String s: splitted){
                choices.add(s);
            }
            Platform.runLater(()-> {
                ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
                dialog.setTitle(BigSwitches.switchIdForName(thiscardid, locale));
                dialog.setHeaderText(rb.getString("interactives_necromancer"));
                dialog.setContentText(rb.getString("interactives_chosenname"));

                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {

                    SimplifiedCard cardfromTable = board.table_StackPaneFree.entrySet().stream().filter(set -> set.getValue().y.name.equals(result.get())).findAny().get().getValue().y;
                    int id = cardfromTable.id;
                    board.client.sendMessage("TakeCardOfType#" + id);
                    System.out.println("Sent: " + "TakeCardOfType#" + id);
                    board.getPlayer().simhand.add(cardfromTable);
                    board.putCardToHand(cardfromTable);
                    board.enableSecondActionButtons(false);
                    //board.client.sendMessage("GOT_CARD_FROM_TABLE#" + id);
                }
                else {
                    // Result is not present - Client pressed Cancel or X
                    board.client.sendMessage("TakeCardOfType#" + -1 + "#" );
                    System.out.println("Sent: " + "TakeCardOfType#" + -1 + "#");
                }
            });
            dialogOpen = false;
        }
        }
    }
}
