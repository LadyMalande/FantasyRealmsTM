package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller class for the second vista.
 */
public class BoardController implements Initializable{

    private List<Button> hand_Canvases;
    private List<StackPane> hand_StackPanes;

    //@FXML
    //private Canvas canvas_hand1;

    @FXML
    private StackPane stack_hand1, stack_hand2, stack_hand3, stack_hand4, stack_hand5, stack_hand6, stack_hand7, stack_hand8;

    @FXML
    private Button hand1, hand2, hand3, hand4, hand5, hand6, hand7, hand8;


    /**
     * Event handler fired when the user requests a previous vista.
     *
     * @param event the event that triggered the handler.
     */
    @FXML
    void previousPane(ActionEvent event) {
        SceneNavigator.loadVista(SceneNavigator.MENU);
    }

    private void create_card(StackPane sp, Card card){
        Bounds bounds = stack_hand1.localToScene(stack_hand1.getBoundsInLocal());
        Canvas canvas_hand1 = new Canvas(150,200);
        GraphicsContext gc = canvas_hand1.getGraphicsContext2D();
        String img_addr;
        switch(card.name){
            case "Unicorn": img_addr = "graphics/unicorn.jpg";
            break;
            default: img_addr = "graphics/icon.jpg";
        }
        Image image1 = new Image(getClass().getResourceAsStream(img_addr));
        gc.drawImage(image1,bounds.getMinX(),bounds.getMinY()+20, 150.0,80.0);
        String color_hex;
        String type_name;
        switch(card.type){
            case CREATURE:  color_hex = "35CA35";
                            type_name = "Creature";
            break;
            case ARMY:      color_hex = "19190E";
                            type_name = "Army";
                break;
            case WEAPON:    color_hex = "FCF842";
                            type_name = "Weapon";
                break;
            case LEADER:    color_hex = "B52F9B";
                            type_name = "Leader";
                break;
            case WIZARD:    color_hex = "DB3975";
                            type_name = "Wizard";
                break;
            case ARTIFACT:  color_hex = "FC8F42";
                            type_name = "Artifact";
                break;
            case FLOOD:     color_hex = "5D3BAD";
                            type_name = "Flood";
                break;
            case EARTH:     color_hex = "4B2600";
                            type_name = "Earth";
                break;
            case FIRE:      color_hex = "FC4242";
                            type_name = "Fire";
                break;
            case WEATHER:   color_hex = "2B8A9B";
                            type_name = "Weather";
                break;
            case WILD:      color_hex = "CBCBC0";
                            type_name = "Wild";
                break;
            default:        color_hex = "FFFFFF";
                            type_name = "fail";
        }
        Stop[] stops = new Stop[] { new Stop(0, Color.web(color_hex)), new Stop(0.5, Color.web("FFFFFF")), new Stop(1, Color.web(color_hex))};
        LinearGradient lg1 = new LinearGradient(0, 0, 1,0, true, CycleMethod.REFLECT, stops);
        gc.setFill(lg1);

        gc.fillRect(bounds.getMinX() ,bounds.getMinY(),20,200);

        gc.setFill(Color.LIGHTGREY);
        gc.fillRect(bounds.getMinX() ,bounds.getMinY(),150,20);
        gc.setFill(Color.IVORY);
        gc.fillOval(bounds.getMinX() - 10 ,bounds.getMinY() - 10,40,40);
        gc.setFill(Color.BLACK);
        gc.fillText(card.name, bounds.getMinX() + 50, bounds.getMinY() + 15);
        gc.fillText(Integer.toString(card.strength), bounds.getMinX() + 5
                , bounds.getMinY() + 15);
        Button b = new Button();

        b.getStyleClass().add("button_card_in_hand");
        gc.setFill(Color.BLACK);
        gc.rotate(-90);
        gc.fillText(type_name, bounds.getMinX()-80, bounds.getMinY() + 14);

        gc.restore();


        sp.getChildren().addAll(canvas_hand1, b);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hand_Canvases = new ArrayList<>();
        hand_Canvases.addAll(Arrays.asList(hand1, hand2, hand3, hand4, hand5, hand6, hand7, hand8));
        hand_StackPanes = new ArrayList<>();
        hand_StackPanes.addAll(Arrays.asList(stack_hand1, stack_hand2, stack_hand3, stack_hand4, stack_hand5, stack_hand6, stack_hand7, stack_hand8));
        Deck deck = new Deck();
        for(StackPane sp : hand_StackPanes){
            create_card(sp, deck.deck.get(0));
        }

    }
}
