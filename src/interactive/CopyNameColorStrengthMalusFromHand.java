package interactive;

import javafx.application.Platform;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.StackPane;
import client.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class for handling the duplication bonus counterpart from the server.
 * @author Tereza Miklóšová
 */
public class CopyNameColorStrengthMalusFromHand {

    /**
     * If the same dialog is not open already, it shows a new one.
     */
    private static boolean dialogOpen = false;

    /**
     * Shows a dialog to ask the player how he wants to treat the card in question.
     * @param board Board for communication with the changed cards.
     * @param thiscardid Id of the card that invoked this bonus and will have changed its traits probably.
     * @param locale Locale in which to show the UI texts.
     */
    public static void askPlayer(BoardController board, int thiscardid, Locale locale) {
        if (!dialogOpen) {
            List<String> choices = board.getHandStackPanes().values().stream().filter(booleanSimplifiedCardTuple -> !booleanSimplifiedCardTuple.x).map(booleanSimplifiedCardTuple -> booleanSimplifiedCardTuple.y.getName()).collect(Collectors.toList());

        Platform.runLater(()-> {
            ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
            dialog.setTitle(BigSwitches.switchIdForName(thiscardid));
            ResourceBundle rb = ResourceBundle.getBundle("client.UITexts", locale);
            dialog.setHeaderText(rb.getString("interactives_cardnametocopy"));
            dialog.setContentText(rb.getString("interactives_chosenname"));
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {


                SimplifiedCard cardToDraw = BoardController.getPlayer().simhand.stream().filter(card -> card.getId() == thiscardid).findAny().orElse(null);
                String handPane = Objects.requireNonNull(board.getHandStackPanes().entrySet().stream().filter(set -> Objects.nonNull(set.getValue().y)).filter(set -> {
                    assert cardToDraw != null;
                    return set.getValue().y.getId() == cardToDraw.getId();
                }).findAny().orElse(null)).getKey();
                StackPane handPaneForCard;
                handPaneForCard = board.switchNameForStackPane(handPane);


                String name = result.get();
                SimplifiedCard original = Objects.requireNonNull(board.getHandStackPanes().entrySet().stream().filter(set -> Objects.nonNull(set.getValue().y)).filter(set -> set.getValue().y.getName().equals(name)).findAny().orElse(null)).getValue().y;
                cardToDraw.setType(original.getType());
                cardToDraw.setName(name);
                cardToDraw.setStrength(original.getStrength());
                cardToDraw.setAllText(rb.getString("maluses_from_card") + " " + name);

                board.create_card_from_text(handPaneForCard, cardToDraw);
                board.getClient().sendMessage("CopyCardFromHand#" + original.getId() + "#" + thiscardid);
            } else {
                // Result is not present - Client pressed Cancel or X
                board.getClient().sendMessage("CopyCardFromHand#" + -1 + "#" + thiscardid);
            }
        });
            dialogOpen = false;
        }
    }
}
