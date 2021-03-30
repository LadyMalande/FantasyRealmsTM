package interactive;

import javafx.application.Platform;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.StackPane;
import sample.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class CopyNameAndType extends Interactive {

    public int priority = 3;
    public final String text;
    public ArrayList<Type> types;
    private int thiscardid;

    public CopyNameAndType( int id, ArrayList<Type> types) {
        this.text = "Copy name and type of any card of these types: " + giveListOfTypesWithSeparator(types, " or ");
        this.types = types;
        this.thiscardid = id;
    }

    private static ArrayList<String> buildListOfNamesAndTypesFromNames(String[] names){
        ArrayList<String> arr = new ArrayList<>();

        for(String name: names){
            StringBuilder str = new StringBuilder();

            String typeString = BigSwitches.switchCardNameForStringType(name);
            str.append(name).append(" (").append(typeString).append(")");
            arr.add(str.toString());
        }

        return arr;
    }
    private static boolean dialogOpen = false;
    public static void askPlayer(BoardController board, int thiscardid, String[] names) {
        // Traditional way to get the response value.
        if (!dialogOpen) {
        Platform.runLater(()-> {
            List<String> choices2 = new ArrayList<>();
            choices2.addAll(buildListOfNamesAndTypesFromNames(names));

            ChoiceDialog<String> dialog2 = new ChoiceDialog<>(choices2.get(0), choices2);
            dialog2.setTitle(BigSwitches.switchIdForName(thiscardid));
            dialog2.setHeaderText("Choose card name to copy");
            dialog2.setContentText("Chosen name:");
            Optional<String> result2 = dialog2.showAndWait();
            if (result2.isPresent()) {
                board.client.sendMessage("CopyNameAndType#" + thiscardid + "#" + result2.get());
                System.out.println("Sent: " + "CopyNameAndType#" + thiscardid + "#" + result2.get());

                SimplifiedCard cardToDraw = board.getPlayer().simhand.stream().filter(card -> card.id == thiscardid).findAny().get();
                System.out.println("cardToDraw: " + thiscardid + " Name: " + cardToDraw.id);
                String handPane = board.hand_StackPaneFree.entrySet().stream().filter(set -> Objects.nonNull(set.getValue().y)).filter(set -> set.getValue().y.id == thiscardid).findAny().get().getKey();
                StackPane handPaneForCard;
                handPaneForCard = board.switchNameForStackPane(handPane);

                String[] splitted = result2.get().split("( \\()");
                String name = splitted[0];
                cardToDraw.type = BigSwitches.switchCardNameForStringType(name);
                cardToDraw.name = name;
                board.create_card_from_text(handPaneForCard, cardToDraw);
            }
        });
            dialogOpen = false;
        }
    }
}
