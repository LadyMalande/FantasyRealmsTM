package interactive;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import client.*;

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



        if(splitted == null){

            Platform.runLater(()-> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(BigSwitches.switchIdForName(thiscardid));

                alert.setHeaderText("There are no cards of given type on the table, you cannot choose any to add to your hand.");
                alert.setContentText("To continue, please click OK.");

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
                dialog.setTitle(BigSwitches.switchIdForName(thiscardid));
                dialog.setHeaderText("Choose card name to get from table");
                dialog.setContentText("Chosen name:");

                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {
                    board.client.sendMessage("TakeCardOfType#" + result.get());
                    System.out.println("Sent: " + "TakeCardOfType#" + result.get());
                    SimplifiedCard cardfromTable = board.table_StackPaneFree.entrySet().stream().filter(set -> set.getValue().y.name.equals(result.get())).findAny().get().getValue().y;
                    int id = cardfromTable.id;
                    board.getPlayer().simhand.add(cardfromTable);
                    board.putCardToHand(cardfromTable);
                    board.enableSecondActionButtons(false);
                    //board.client.sendMessage("GOT_CARD_FROM_TABLE#" + id);
                }
            });
            dialogOpen = false;
        }
        }
    }
}
