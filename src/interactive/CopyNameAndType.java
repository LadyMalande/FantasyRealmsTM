package interactive;

import javafx.scene.control.ChoiceDialog;
import sample.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CopyNameAndType extends Interactive {
    public int priority = 2;
    public String text;
    public ArrayList<Type> types;
    private int thiscardid;

    public CopyNameAndType( int id, ArrayList<Type> types) {
        this.text = "Copy name and type of any card of these types: " + giveListOfTypesWithSeparator(types, " or ");
        this.types = types;
        this.thiscardid = id;
    }

    @Override
    public String getText(){
        return this.text;
    }

    @Override
    public void askPlayer() {
        /*List<String> choices = new ArrayList<>();
        choices.addAll(ArrayListCreator.createListOfStringsFromTypes(types));

        ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
        dialog.setTitle(BigSwitches.switchIdForName(thiscardid));
        dialog.setHeaderText("Choose Type to copy");
        dialog.setContentText("Chosen type:");
*/
// Traditional way to get the response value.
       // Optional<String> result = dialog.showAndWait();

            List<String> choices2 = new ArrayList<>();
            choices2.addAll(ArrayListCreator.createListOfNamesFromTypes(types));

            ChoiceDialog<String> dialog2 = new ChoiceDialog<>(choices2.get(0), choices2);
            dialog2.setTitle(BigSwitches.switchIdForName(thiscardid));
            dialog2.setHeaderText("Choose card name to copy");
            dialog2.setContentText("Chosen name:");
            Optional<String> result2 = dialog2.showAndWait();
        if (result2.isPresent()){

            BoardController.player.hand.stream().filter(card -> card.id == thiscardid).findAny().get().name = result2.get();
            System.out.println(BoardController.player.hand.stream().filter(card -> card.id == thiscardid).findAny().get().name);
            System.out.println(BigSwitches.switchTypeForName(DeckInitializer.loadDeckFromFile().stream().filter(card -> card.name.equals(result2.get())).findAny().get().type));
            BoardController.player.hand.stream().filter(card -> card.id == thiscardid).findAny().get().id = DeckInitializer.loadDeckFromFile().stream().filter(card -> card.name.equals(result2.get())).findAny().get().id;

        }

    }
}
