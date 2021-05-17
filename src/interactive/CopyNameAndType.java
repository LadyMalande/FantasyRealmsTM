package interactive;

import javafx.application.Platform;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.StackPane;
import client.*;

import java.util.*;

/**
 * Class for handling the copy name and type bonus counterpart from the server.
 * @author Tereza Miklóšová
 */
public class CopyNameAndType{

    /**
     * If the same dialog is not open already, it shows a new one.
     */
    private static boolean dialogOpen = false;

    /**
     * Builds an ArrayList of strings in fashion of: Name (Type).
     * @param names Names which we want to enrich by type.
     * @param locale Locale for the language we want the list in.
     * @return List of options for player to choose from. Element is: "Name (Type)".
     */
    private static ArrayList<String> buildListOfNamesAndTypesFromNames(String[] names, Locale locale){
        ArrayList<String> arr = new ArrayList<>();

        for(String name: names){
            StringBuilder str = new StringBuilder();

            String typeString = BigSwitches.switchCardNameForStringType(name, locale);
            str.append(name).append(" (").append(typeString).append(")");
            arr.add(str.toString());
        }

        return arr;
    }

    /**
     * Shows a dialog to ask the player how he wants to treat the card in question.
     * @param board Board for communication with the changed cards.
     * @param thiscardid Id of the card that invoked this bonus.
     * @param names Names given by the server that are possible to change this card to.
     * @param locale Locale in which to show the UI texts.
     */
    public static void askPlayer(BoardController board, int thiscardid, String[] names, Locale locale) {
        // Traditional way to get the response value.
        if (!dialogOpen) {
        Platform.runLater(()-> {
            List<String> choices2 = new ArrayList<>(buildListOfNamesAndTypesFromNames(names, locale));

            ChoiceDialog<String> dialog2 = new ChoiceDialog<>(choices2.get(0), choices2);
            dialog2.setTitle(BigSwitches.switchIdForName(thiscardid, locale));
            ResourceBundle rb = ResourceBundle.getBundle("client.UITexts", locale);
            dialog2.setHeaderText(rb.getString("interactives_cardnametocopy"));
            dialog2.setContentText(rb.getString("interactives_chosenname"));
            Optional<String> result2 = dialog2.showAndWait();
            if (result2.isPresent()) {
                board.getClient().sendMessage("CopyNameAndType#" + thiscardid + "#" + result2.get());

                SimplifiedCard cardToDraw = BoardController.getPlayer().simhand.stream().filter(card -> card.getId() == thiscardid).findAny().orElse(null);
                String handPane = Objects.requireNonNull(board.getHandStackPanes().entrySet().stream().filter(set -> Objects.nonNull(set.getValue().y)).filter(set -> set.getValue().y.getId() == thiscardid).findAny().orElse(null)).getKey();
                StackPane handPaneForCard;
                handPaneForCard = board.switchNameForStackPane(handPane);

                String[] splitted = result2.get().split("( \\()");
                String name = splitted[0];
                assert cardToDraw != null;
                cardToDraw.setType(BigSwitches.switchCardNameForStringType(name, locale));
                cardToDraw.setName(name);
                board.create_card_from_text(handPaneForCard, cardToDraw);
            }
            else {
                // Result is not present - Client pressed Cancel or X
                board.getClient().sendMessage("CopyNameAndType#" + -1 + "#" );
            }
        });
            dialogOpen = false;
        }
    }
}
