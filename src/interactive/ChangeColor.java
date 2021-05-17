package interactive;

import javafx.application.Platform;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.StackPane;
import client.*;

import java.util.*;

/**
 * Class for handling the change color bonus counterpart from the server.
 * @author Tereza Miklóšová
 */
public class ChangeColor {

    /**
     * If the same dialog is not open already, it shows a new one.
     */
    private static boolean dialogOpen = false;

    /**
     * Shows a dialog to ask the player how he wants to treat the card in question.
     * @param board Board for communication with the changed cards.
     * @param id Id of the card that invoked this bonus.
     * @param locale Locale in which to show the UI texts.
     */
    public static void askPlayer(BoardController board, int id, Locale locale) {
        //Choose card in hand
        if (!dialogOpen) {
        List<String> choices = new ArrayList<>();
        //choices.addAll(board.hand_StackPaneFree.entrySet().stream().filter(set -> !set.getValue().x).map(simcard -> simcard.getValue().y.name).collect(Collectors.toList()));
        for(SimplifiedCard simcard : BoardController.getPlayer().simhand){
            if(simcard.getId() != 31) {
                // You cant change Book of Changes itself
                choices.add(simcard.getName());
            }
        }
            ResourceBundle rb = ResourceBundle.getBundle("client.UITexts", locale);
        Platform.runLater(()-> {
        ChoiceDialog<String> dialog1 = new ChoiceDialog<>(choices.get(0), choices);
        dialog1.setTitle(BigSwitches.switchIdForName(id, locale));
        dialog1.setHeaderText(rb.getString("interactives_choosecard"));
        dialog1.setContentText(rb.getString("interactives_cardwillchangecolor"));

        //Choose color
        List<String> choices2 = new ArrayList<>();
        choices2.add(BigSwitches.switchTypeForName(Type.ARMY, locale));
        choices2.add(BigSwitches.switchTypeForName(Type.ARTIFACT, locale));
        choices2.add(BigSwitches.switchTypeForName(Type.BEAST, locale));
        choices2.add(BigSwitches.switchTypeForName(Type.FLOOD, locale));
        choices2.add(BigSwitches.switchTypeForName(Type.FLAME, locale));
        choices2.add(BigSwitches.switchTypeForName(Type.WEATHER, locale));
        choices2.add(BigSwitches.switchTypeForName(Type.WEAPON, locale));
        choices2.add(BigSwitches.switchTypeForName(Type.LEADER, locale));
        choices2.add(BigSwitches.switchTypeForName(Type.LAND, locale));
        choices2.add(BigSwitches.switchTypeForName(Type.WIZARD, locale));
        choices2.add(BigSwitches.switchTypeForName(Type.WILD, locale));


        ChoiceDialog<String> dialog2 = new ChoiceDialog<>(choices2.get(0), choices2);
        dialog2.setTitle(BigSwitches.switchIdForName(id, locale));

        dialog2.setHeaderText(rb.getString("interactives_colorchange_header"));
        dialog2.setContentText(rb.getString("interactives_colorchangecontent"));

            // Traditional way to get the response value.

            Optional<String> whichCardToChange = dialog1.showAndWait();
            Optional<String> typeForChange = dialog2.showAndWait();
            if (whichCardToChange.isPresent() && typeForChange.isPresent()) {


                SimplifiedCard cardToDraw = BoardController.getPlayer().simhand.stream().filter(card -> card.getName().equals(whichCardToChange.get())).findAny().orElse(null);
                String handPane = Objects.requireNonNull(board.getHandStackPanes().entrySet().stream().filter(set -> Objects.nonNull(set.getValue().y)).filter(set -> {
                    assert cardToDraw != null;
                    return set.getValue().y.getId() == cardToDraw.getId();
                }).findAny().orElse(null)).getKey();
                StackPane handPaneForCard;
                handPaneForCard = board.switchNameForStackPane(handPane);

                cardToDraw.setType(typeForChange.get());
                board.getClient().sendMessage("ChangeColor#" + cardToDraw.getId() + "#" + typeForChange.get());
                System.out.println("Sent: " + "ChangeColor#" + cardToDraw.getId() + "#" + typeForChange.get());
                board.create_card_from_text(handPaneForCard, cardToDraw);
            }
            else {
                // Result is not present - Client pressed Cancel or X
                board.getClient().sendMessage("ChangeColor#" + -1 + "#" );
                System.out.println("Sent: " + "ChangeColor#" + -1 + "#");
            }
        });
        dialogOpen = false;
    }
    }
}
