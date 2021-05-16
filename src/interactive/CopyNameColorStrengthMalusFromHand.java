package interactive;

import javafx.application.Platform;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.StackPane;
import client.*;

import java.util.*;
import java.util.stream.Collectors;

public class CopyNameColorStrengthMalusFromHand extends Interactive  {

    private static boolean dialogOpen = false;
    public static void askPlayer(BoardController board, int thiscardid, Locale locale) {
        if (!dialogOpen) {
            List<String> choices = board.hand_StackPaneFree.values().stream().filter(booleanSimplifiedCardTuple -> !booleanSimplifiedCardTuple.x).map(booleanSimplifiedCardTuple -> booleanSimplifiedCardTuple.y.name).collect(Collectors.toList());

        Platform.runLater(()-> {
            ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
            dialog.setTitle(BigSwitches.switchIdForName(thiscardid));
            ResourceBundle rb = ResourceBundle.getBundle("client.UITexts", locale);
            dialog.setHeaderText(rb.getString("interactives_cardnametocopy"));
            dialog.setContentText(rb.getString("interactives_chosenname"));
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {


                SimplifiedCard cardToDraw = board.getPlayer().simhand.stream().filter(card -> card.id == thiscardid).findAny().orElse(null);
                String handPane = Objects.requireNonNull(board.hand_StackPaneFree.entrySet().stream().filter(set -> Objects.nonNull(set.getValue().y)).filter(set -> {
                    assert cardToDraw != null;
                    return set.getValue().y.id == cardToDraw.id;
                }).findAny().orElse(null)).getKey();
                StackPane handPaneForCard;
                handPaneForCard = board.switchNameForStackPane(handPane);


                String name = result.get();
                SimplifiedCard original = Objects.requireNonNull(board.hand_StackPaneFree.entrySet().stream().filter(set -> Objects.nonNull(set.getValue().y)).filter(set -> set.getValue().y.name.equals(name)).findAny().orElse(null)).getValue().y;
                cardToDraw.type = original.type;
                cardToDraw.name = name;
                cardToDraw.strength = original.strength;
                cardToDraw.allText = rb.getString("maluses_from_card") + " " + name;

                board.create_card_from_text(handPaneForCard, cardToDraw);
                board.client.sendMessage("CopyCardFromHand#" + original.id + "#" + thiscardid);
            } else {
                // Result is not present - Client pressed Cancel or X
                board.client.sendMessage("CopyCardFromHand#" + -1 + "#" + thiscardid);
            }
        });
            dialogOpen = false;
        }
    }
}
