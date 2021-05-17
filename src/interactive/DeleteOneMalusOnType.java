package interactive;

import javafx.application.Platform;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.StackPane;
import client.*;

import java.util.*;

/**
 * Class for handling the delete penalty on type bonus counterpart from the server.
 * @author Tereza Miklóšová
 */
public class DeleteOneMalusOnType {

    /**
     * If the same dialog is not open already, it shows a new one.
     */
    private static boolean dialogOpen = false;

    /**
     * Shows a dialog to ask the player how he wants to treat the card in question.
     * @param board Board for communication with the changed cards.
     * @param thiscardid Id of the card that invoked this bonus.
     * @param splitted Strings in the fashion of: "Card name: Penalty text to remove".
     * @param locale Locale in which to show the UI texts.
     */
    public static void askPlayer(BoardController board, int thiscardid, String[] splitted, Locale locale) {
        if (!dialogOpen) {
        List<String> choices = new ArrayList<>();

            Collections.addAll(choices, splitted);
        Platform.runLater(()-> {
            ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
            dialog.setTitle(BigSwitches.switchIdForName(thiscardid , locale));
            ResourceBundle rb = ResourceBundle.getBundle("client.UITexts", locale);
            dialog.setHeaderText(rb.getString("interactives_removemalus"));
            dialog.setContentText(rb.getString("interactives_chosencard"));
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {

                String[] option = result.get().split(": ");
                String cardName = option[0];
                String malus = option[1];
                SimplifiedCard cardfromHand = Objects.requireNonNull(board.getHandStackPanes().entrySet().stream().filter(set -> Objects.nonNull(set.getValue().y)).filter(set -> set.getValue().y.getName().equals(cardName)).findAny().orElse(null)).getValue().y;
                board.getClient().sendMessage("DeleteOneMalusOnType#" + cardfromHand.getId() + ": " + malus);

                String textOnCard = cardfromHand.getAllText();
                textOnCard = textOnCard.replace(option[1], "");
                cardfromHand.setAllText(textOnCard);

                String handPane = Objects.requireNonNull(board.getHandStackPanes().entrySet().stream().filter(set -> Objects.nonNull(set.getValue().y)).filter(set -> set.getValue().y.getId() == cardfromHand.getId()).findAny().orElse(null)).getKey();
                StackPane handPaneForCard;
                handPaneForCard = board.switchNameForStackPane(handPane);

                board.create_card_from_text(handPaneForCard, cardfromHand);
            }
            else {
                // Result is not present - Client pressed Cancel or X
                board.getClient().sendMessage("DeleteOneMalusOnType#" + -1 + ": ");
            }
        });
            dialogOpen = false;
        }
    }
}
