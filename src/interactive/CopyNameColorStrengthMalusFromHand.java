package interactive;

import javafx.application.Platform;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.StackPane;
import sample.*;

import java.util.*;
import java.util.stream.Collectors;

public class CopyNameColorStrengthMalusFromHand extends Interactive  {

    public int priority = 4;
    public final String text = "Copy name, type, strength and malus of any card in your hand";
    private int thiscardid;

    public String getText(){
        return this.text;
    }
    public CopyNameColorStrengthMalusFromHand(int id){
        this.thiscardid = id;
    }

    private static boolean dialogOpen = false;
    public static void askPlayer(BoardController board, int thiscardid, Locale locale) {
        if (!dialogOpen) {
        List<String> choices = new ArrayList<>();
        choices.addAll(board.hand_StackPaneFree.entrySet().stream().filter(set -> !set.getValue().x).map(simcard -> simcard.getValue().y.name).collect(Collectors.toList()));

        Platform.runLater(()-> {
            ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
            dialog.setTitle(BigSwitches.switchIdForName(thiscardid));
            ResourceBundle rb = ResourceBundle.getBundle("sample.UITexts", locale);
            dialog.setHeaderText(rb.getString("interactives_cardnametocopy"));
            dialog.setContentText(rb.getString("interactives_chosenname"));
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {


                SimplifiedCard cardToDraw = board.getPlayer().simhand.stream().filter(card -> card.id == thiscardid).findAny().get();
                System.out.println("thiscardid: " + thiscardid + " Name: " + cardToDraw.name);
                String handPane = board.hand_StackPaneFree.entrySet().stream().filter(set -> Objects.nonNull(set.getValue().y)).filter(set -> set.getValue().y.id == cardToDraw.id).findAny().get().getKey();
                StackPane handPaneForCard;
                handPaneForCard = board.switchNameForStackPane(handPane);


                String name = result.get();
                SimplifiedCard original = board.hand_StackPaneFree.entrySet().stream().filter(set -> Objects.nonNull(set.getValue().y)).filter(set -> set.getValue().y.name.equals(name)).findAny().get().getValue().y;
                cardToDraw.type = original.type;
                cardToDraw.name = name;
                cardToDraw.strength = original.strength;
                String[] alltext = original.allText.split("MALUS\n");
                if(alltext.length > 1){
                    cardToDraw.allText = alltext[1];
                } else{
                    cardToDraw.allText = "";
                }
                board.create_card_from_text(handPaneForCard, cardToDraw);

                board.client.sendMessage("CopyCardFromHand#" + original.id + "#" + thiscardid);
                System.out.println("Sent: " + "CopyCardFromHand#" + original.id + "#" + thiscardid);
            } else {
                // Result is not present - Client pressed Cancel or X
                board.client.sendMessage("CopyCardFromHand#" + -1 + "#" + thiscardid);
                System.out.println("Sent: " + "CopyCardFromHand#" + -1 + "#" + thiscardid);
            }
        });
            dialogOpen = false;
        }
    }
}
