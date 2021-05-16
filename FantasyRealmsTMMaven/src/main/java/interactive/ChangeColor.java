package interactive;

import client.BigSwitches;
import client.BoardController;
import client.SimplifiedCard;
import client.Type;
import javafx.application.Platform;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.StackPane;

import java.util.*;

public class ChangeColor extends Interactive {

    private static boolean dialogOpen = false;
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static void askPlayer(BoardController board, int id, Locale locale) {
        //Choose card in hand
        if (!dialogOpen) {
        List<String> choices = new ArrayList<>();
        //choices.addAll(board.hand_StackPaneFree.entrySet().stream().filter(set -> !set.getValue().x).map(simcard -> simcard.getValue().y.name).collect(Collectors.toList()));
        for(SimplifiedCard simcard : board.getPlayer().simhand){
            if(simcard.id != 31) {
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


                SimplifiedCard cardToDraw = board.getPlayer().simhand.stream().filter(card -> card.name.equals(whichCardToChange.get())).findAny().get();
                String handPane = board.hand_StackPaneFree.entrySet().stream().filter(set -> Objects.nonNull(set.getValue().y)).filter(set -> set.getValue().y.id == cardToDraw.id).findAny().get().getKey();
                StackPane handPaneForCard;
                handPaneForCard = board.switchNameForStackPane(handPane);

                cardToDraw.type = typeForChange.get();
                board.client.sendMessage("ChangeColor#" + cardToDraw.id + "#" + typeForChange.get());
                System.out.println("Sent: " + "ChangeColor#" + cardToDraw.id + "#" + typeForChange.get());
                board.create_card_from_text(handPaneForCard, cardToDraw);
            }
            else {
                // Result is not present - Client pressed Cancel or X
                board.client.sendMessage("ChangeColor#" + -1 + "#" );
                System.out.println("Sent: " + "ChangeColor#" + -1 + "#");
            }
        });
        dialogOpen = false;
    }

    }
}
