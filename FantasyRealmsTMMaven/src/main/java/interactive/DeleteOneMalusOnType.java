package interactive;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.StackPane;
import sample.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DeleteOneMalusOnType extends Interactive {

    public int priority = 1;
    public final String text;
    public ArrayList<Type> types;
    private int thiscardid;

    public DeleteOneMalusOnType(int id, ArrayList<Type> types) {
        this.text = "Delete one malus on card of type " + giveListOfTypesWithSeparator(types, " or ");
        this.types = types;
        this.thiscardid = id;
    }
    private static boolean dialogOpen = false;
    public static void askPlayer(BoardController board, int thiscardid, String[] splitted) {
        if (!dialogOpen) {
        List<String> choices = new ArrayList<>();

        for(String s: splitted){
            choices.add(s);
        }
        Platform.runLater(()-> {
            ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
            dialog.setTitle(BigSwitches.switchIdForName(thiscardid));
            dialog.setHeaderText("Choose card on which to remove one malus");
            dialog.setContentText("Chosen card:");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                board.client.sendMessage("DeleteOneMalusOnType#" + result.get());
                System.out.println("Sent: " + "DeleteOneMalusOnType#" + result.get());
                String[] option = result.get().split(": ");
                String cardName = option[0];
                System.out.println("Name: " + option[0]);


                System.out.println(board.hand_StackPaneFree.get("stack_hand1").y);
                System.out.println(board.hand_StackPaneFree.get("stack_hand2").y);
                System.out.println(board.hand_StackPaneFree.get("stack_hand3").y);
                System.out.println(board.hand_StackPaneFree.get("stack_hand4").y);
                System.out.println(board.hand_StackPaneFree.get("stack_hand5").y);
                System.out.println(board.hand_StackPaneFree.get("stack_hand6").y);
                System.out.println(board.hand_StackPaneFree.get("stack_hand7").y);
                System.out.println(board.hand_StackPaneFree.get("stack_hand8").y);


                SimplifiedCard cardfromHand = board.hand_StackPaneFree.entrySet().stream().filter(set -> Objects.nonNull(set.getValue().y)).filter(set -> set.getValue().y.name.equals(cardName)).findAny().get().getValue().y;
                String textOnCard = cardfromHand.allText;
                textOnCard = textOnCard.replace(option[1], "");
                cardfromHand.allText = textOnCard;

                String handPane = board.hand_StackPaneFree.entrySet().stream().filter(set -> Objects.nonNull(set.getValue().y)).filter(set -> set.getValue().y.id == cardfromHand.id).findAny().get().getKey();
                StackPane handPaneForCard;
                handPaneForCard = board.switchNameForStackPane(handPane);


                board.create_card_from_text(handPaneForCard, cardfromHand);



            }
        });
            dialogOpen = false;
        }
    }
}
