package interactive;

import javafx.application.Platform;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.StackPane;
import client.*;

import java.util.*;

public class DeleteOneMalusOnType extends Interactive {

    private static boolean dialogOpen = false;
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
                SimplifiedCard cardfromHand = Objects.requireNonNull(board.hand_StackPaneFree.entrySet().stream().filter(set -> Objects.nonNull(set.getValue().y)).filter(set -> set.getValue().y.name.equals(cardName)).findAny().orElse(null)).getValue().y;
                board.client.sendMessage("DeleteOneMalusOnType#" + cardfromHand.id + ": " + malus);

                String textOnCard = cardfromHand.allText;
                textOnCard = textOnCard.replace(option[1], "");
                cardfromHand.allText = textOnCard;

                String handPane = Objects.requireNonNull(board.hand_StackPaneFree.entrySet().stream().filter(set -> Objects.nonNull(set.getValue().y)).filter(set -> set.getValue().y.id == cardfromHand.id).findAny().orElse(null)).getKey();
                StackPane handPaneForCard;
                handPaneForCard = board.switchNameForStackPane(handPane);

                board.create_card_from_text(handPaneForCard, cardfromHand);
            }
            else {
                // Result is not present - Client pressed Cancel or X
                board.client.sendMessage("DeleteOneMalusOnType#" + -1 + ": ");
            }
        });
            dialogOpen = false;
        }
    }
}
