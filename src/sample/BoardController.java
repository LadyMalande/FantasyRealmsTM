package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import bonuses.Bonus;
import maluses.Malus;
import interactive.Interactive;


import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;

/**
 * Controller class for the second vista.
 */
public class BoardController implements Initializable{

    private List<Button> hand_Canvases;
    private List<StackPane> hand_StackPanes;
    private Random randomGenerator;
    public static Player player;

    public Player getPlayer(){
        return player;
    }


    @FXML
    private StackPane stack_hand1, stack_hand2, stack_hand3, stack_hand4, stack_hand5, stack_hand6, stack_hand7, stack_hand8;

    @FXML
    private Button hand1, hand2, hand3, hand4, hand5, hand6, hand7, hand8;

    public static Deck deck;

    public Deck getDeck(){
        return deck;
    }
    /**
     * Event handler fired when the user requests a previous vista.
     *
     * @param event the event that triggered the handler.
     */
    @FXML
    void backToMenu(ActionEvent event) {
        SceneNavigator.loadVista(SceneNavigator.MENU);
    }

    private void create_card(StackPane sp, Card card){
        Bounds bounds = stack_hand1.localToScene(stack_hand1.getBoundsInLocal());
        Canvas canvas_hand1 = new Canvas(150,200);
        GraphicsContext gc = canvas_hand1.getGraphicsContext2D();

        //Decide the image address based on name of the card and draw it on the canvas
        String img_addr = BigSwitches.switchImage(card.id);

        Image image1 = new Image(getClass().getResourceAsStream(img_addr));
        gc.drawImage(image1,bounds.getMinX(),bounds.getMinY()+20, 150.0,80.0);

        //Decide the color and String of type rectangle based on Type enum
        ArrayList<String> typeColorAndName = BigSwitches.switchType(card.type);
        String color_hex = typeColorAndName.get(0);
        String type_name = typeColorAndName.get(1);


        //Make the type rectangle with LinearGradient fill
        Stop[] stops = new Stop[] { new Stop(0, Color.web(color_hex)), new Stop(0.5, Color.web("FFFFFF")), new Stop(1, Color.web(color_hex))};
        LinearGradient lg1 = new LinearGradient(0, 0, 1,0, true, CycleMethod.REFLECT, stops);
        gc.setFill(lg1);
        gc.fillRect(bounds.getMinX() ,bounds.getMinY(),20,200);

        gc.setFill(Color.LIGHTGREY);
        gc.fillRect(bounds.getMinX() ,bounds.getMinY(),150,20);
        gc.setFill(Color.IVORY);
        gc.fillOval(bounds.getMinX() - 10 ,bounds.getMinY() - 10,40,40);
        gc.setFill(Color.BLACK);

        gc.fillText(card.name, bounds.getMinX() + 35, bounds.getMinY() + 15);
        gc.fillText(Integer.toString(card.strength), bounds.getMinX() + 5
                , bounds.getMinY() + 15);

        //Set button over the canvas and style it
        Button b = new Button();
        b.getStyleClass().add("button_card_in_hand");
        gc.setFill(Color.BLACK);

        //Rotated type text
        gc.rotate(-90);
        gc.fillText(type_name, bounds.getMinX()-80, bounds.getMinY() + 14);
        gc.restore();

        String allText = "";


        if(card.bonuses != null){
            allText += "BONUS:\n";
            for(var bonus: card.bonuses){


                allText += bonus.getText();
                allText += "\n";
            }
        }
        if(card.maluses != null){
            for(Malus malus: card.maluses){
                allText += malus.text;
                allText += "&#13;";
            }
        }
        if(card.interactives != null){
            for(Interactive inter: card.interactives){
                allText += inter.text;
                allText += "&#13;";
            }
        }
        Label text = new Label(allText);
        text.setWrapText(true);
        text.setTranslateY(50);
        text.setTranslateX(10);
        text.setMaxSize(110,150);
        text.setAlignment(Pos.CENTER);


        sp.getChildren().addAll(canvas_hand1, b, text);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hand_Canvases = new ArrayList<>();
        hand_Canvases.addAll(Arrays.asList(hand1, hand2, hand3, hand4, hand5, hand6, hand7, hand8));
        hand_StackPanes = new ArrayList<>();
        hand_StackPanes.addAll(Arrays.asList(stack_hand1, stack_hand2, stack_hand3, stack_hand4, stack_hand5, stack_hand6, stack_hand7, stack_hand8));
        deck = new Deck();
        randomGenerator = new Random();
        Player p = new Player(createHandForPlayer());
        System.out.println("In hand there is " + Integer.toString(p.hand.size()) + " cards.");
        Iterator<Card> iter = p.hand.iterator();

        for(StackPane sp : hand_StackPanes){
            if(iter.hasNext()) {
                create_card(sp, iter.next());
            }
        }
    }

    public ArrayList<Card> createHandForPlayer(){
        ArrayList<Card> hand = new ArrayList<>();
        System.out.println(deck.getDeck().size());
        //Randomly choose 7 cards from remaining deck
        for(int i = 0; i < 7;i++){
            int index = randomGenerator.nextInt(deck.getDeck().size());
            hand.add(deck.getDeck().get(index));
            deck.getDeck().remove(index);
        }
        return hand;
    }
}
