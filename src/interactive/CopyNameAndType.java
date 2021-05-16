package interactive;

import javafx.application.Platform;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.StackPane;
import client.*;

import java.util.*;

public class CopyNameAndType extends Interactive {

    public final String text;
    public ArrayList<Type> types;

    public CopyNameAndType( ArrayList<Type> types) {
        this.text = "Copy name and type of any card of these types: " + giveListOfTypesWithSeparator(types, " or ");
        this.types = types;
    }

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
    private static boolean dialogOpen = false;
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
                board.client.sendMessage("CopyNameAndType#" + thiscardid + "#" + result2.get());

                SimplifiedCard cardToDraw = board.getPlayer().simhand.stream().filter(card -> card.id == thiscardid).findAny().orElse(null);
                String handPane = Objects.requireNonNull(board.hand_StackPaneFree.entrySet().stream().filter(set -> Objects.nonNull(set.getValue().y)).filter(set -> set.getValue().y.id == thiscardid).findAny().orElse(null)).getKey();
                StackPane handPaneForCard;
                handPaneForCard = board.switchNameForStackPane(handPane);

                String[] splitted = result2.get().split("( \\()");
                String name = splitted[0];
                assert cardToDraw != null;
                cardToDraw.type = BigSwitches.switchCardNameForStringType(name, locale);
                cardToDraw.name = name;
                board.create_card_from_text(handPaneForCard, cardToDraw);
            }
            else {
                // Result is not present - Client pressed Cancel or X
                board.client.sendMessage("CopyNameAndType#" + -1 + "#" );
            }
        });
            dialogOpen = false;
        }
    }
}
