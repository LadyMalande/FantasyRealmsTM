package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import maluses.Malus;
import interactive.Interactive;


import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Controller class for the second vista.
 */
public class BoardController implements Initializable{

    private String thisPlayername;
    private int thisPlayerMaxPlayers;
    private List<Button> hand_Buttons;
    public  static List<StackPane> hand_StackPanes;
    public static List<StackPane> table_StackPanes;
    Client client;
    private Random randomGenerator;
    public static Player player;

    public Player getPlayer(){
        return player;
    }

    public boolean gameOver = false;
    public boolean yourTurn = true;
    public static boolean gotAllCards = false;
    @FXML
    public StackPane stack_hand1, stack_hand2, stack_hand3, stack_hand4, stack_hand5, stack_hand6, stack_hand7, stack_hand8;

    @FXML
    public StackPane stack_table1, stack_table2, stack_table3, stack_table4, stack_table5, stack_table6, stack_table7, stack_table8, stack_table9, stack_table90, stack_deck;

    @FXML
    private Button deck_Button;

    @FXML
    private Label label_score;

    @FXML
    private HBox hand_HBox;

    public static ArrayList<Card> cardsOnTable;

    public static Deck deck;

    // map having name of component where it resides and <Free?, If not what card it contains>
    private TreeMap<String, Tuple<Boolean,SimplifiedCard>> hand_StackPaneFree = new TreeMap<>();
    private TreeMap<String, Tuple<Boolean, SimplifiedCard>> table_StackPaneFree = new TreeMap<>();

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

    private void create_card_from_text(StackPane sp, SimplifiedCard card){

        Bounds bounds = sp.localToScene(sp.getBoundsInLocal());
        String id = String.valueOf(card.id);
        String name = card.name;
        String type = card.type;
        String strength = String.valueOf(card.strength);
        String cardText = card.allText;
        Canvas canvas_hand1 = new Canvas(150,200);
        GraphicsContext gc = canvas_hand1.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 150,200);
        //Decide the image address based on name of the card and draw it on the canvas
        String img_addr = BigSwitches.switchImage(Integer.parseInt(id));

        Image image1 = new Image(getClass().getResourceAsStream(img_addr));
        gc.drawImage(image1,0,20, 150.0,80.0);

        //Decide the color and String of type rectangle based on Type enum
        ArrayList<String> typeColorAndName = BigSwitches.switchType(BigSwitches.switchNameForType(type));
        String color_hex = typeColorAndName.get(0);
        String type_name = typeColorAndName.get(1);


        //Make the type rectangle with LinearGradient fill
        Stop[] stops = new Stop[] { new Stop(0, Color.web(color_hex)), new Stop(0.5, Color.web("FFFFFF")), new Stop(1, Color.web(color_hex))};
        LinearGradient lg1 = new LinearGradient(0, 0, 1,0, true, CycleMethod.REFLECT, stops);
        gc.setFill(lg1);
        gc.fillRect(0 ,0,20,200);

        gc.setFill(Color.LIGHTGREY);
        gc.fillRect(0 ,0,150,20);
        gc.setFill(Color.IVORY);
        gc.fillOval(0 - 10 ,0- 10,40,40);
        gc.setFill(Color.BLACK);

        gc.fillText(name, 35, 15);
        gc.fillText(strength, 5
                , 15);

        //Set button over the canvas and style it
        Button b = new Button();
        b.getStyleClass().add("button_card_in_hand");
        b.setOnMouseClicked(this::dropCard);
        gc.setFill(Color.BLACK);

        //Rotated type text
        gc.rotate(-90);
        gc.fillText(type_name, -80, 14);
        gc.restore();


        Label text = new Label(cardText);
        text.setWrapText(true);
        text.setTranslateY(50);
        text.setTranslateX(10);
        text.setMaxSize(110,150);
        text.setAlignment(Pos.CENTER);
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 10));

        sp.getChildren().addAll(canvas_hand1,text, b);

    }

    private void play(){
        while(!gameOver){
            if(yourTurn){
                for(Button b: hand_Buttons){
                    b.setDisable(false);
                }

            }
            else{
                for(Button b: hand_Buttons){
                    b.setDisable(true);
                }
            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        show_dialog_HowManyPlayers();
        show_dialog_InsertYourName();


        player = new Player(new ArrayList<>());
        client = new Client(thisPlayername, thisPlayerMaxPlayers, this);
        gotAllCards = false;
        System.out.println("After init client");
        Thread t = new Thread(client);
        t.start();

        System.out.println("after client.communicate");

        cardsOnTable = new ArrayList<>();
        hand_StackPanes = new ArrayList<>();
        hand_StackPanes.addAll(Arrays.asList(stack_hand1, stack_hand2, stack_hand3, stack_hand4, stack_hand5, stack_hand6, stack_hand7, stack_hand8));
        table_StackPanes = new ArrayList<>();
        table_StackPanes.addAll(Arrays.asList(stack_table1, stack_table2, stack_table3, stack_table4, stack_table5, stack_table6, stack_table7, stack_table8, stack_table9, stack_table90));

        for(StackPane s: table_StackPanes){
            table_StackPaneFree.put(s.getId(), new Tuple<>(true,null));
        }
        deck_Button.setOnMouseClicked(e-> getCardFromDeck());

        while(!gotAllCards){
            System.out.println("Waiting for server to get us all cards...");
        }
        System.out.println("after stack panes free");
        crate_hand_SimplifiedCards();

        //hand_Buttons.forEach(button -> button.setOnMouseClicked(click -> dropCard(click)));
        //play();
    }

    private void crate_hand_SimplifiedCards(){
        System.out.println("In create_hand:Simplified cards");
            Iterator<SimplifiedCard> iter = player.simhand.iterator();
            for (StackPane sp : hand_StackPanes) {

                if (iter.hasNext()) {
                    SimplifiedCard card = iter.next();
                    create_card_from_text(sp, card);
                    hand_StackPaneFree.put(sp.getId(), new Tuple<Boolean, SimplifiedCard>(false, card));
                    System.out.println("Number of components in stack pane " + sp.getChildren().size());
                }
            }
        hand_StackPaneFree.put(stack_hand8.getId(),new Tuple<>(true,null));
    }

    public void evaluateHand(){
        //label_score.setText(Integer.toString(player.evaluateHand()));
        // You must send the score to server to make table of winners!!!!
    }


    public void getCardFromDeck(){
        client.sendMessage("GIVE_CARD_FROM_DECK");
    }

    public void putCardOnTable(SimplifiedCard card){
        StackPane table;
        String freeStackPaneOnTable = table_StackPaneFree.entrySet().stream().filter(set-> set.getValue().x).findFirst().get().getKey();
        switch(freeStackPaneOnTable){
            case "stack_table1": table = stack_table1;
                break;
            case "stack_table2": table = stack_table2;
                break;
            case "stack_table3": table = stack_table3;
                break;
            case "stack_table4": table = stack_table4;
                break;
            case "stack_table5": table = stack_table5;
                break;
            case "stack_table6": table = stack_table6;
                break;
            case "stack_table7": table = stack_table7;
                break;
            case "stack_table8": table = stack_table8;
                break;
            case "stack_table9": table = stack_table9;
                break;
            case "stack_table90": table = stack_table90;
                break;
            default: table = null;
                break;
        }

        Button b = new Button();
        b.getStyleClass().add("button_card_on_board");
        b.setOnMouseClicked(f-> getCardFromTable(f));

        table_StackPaneFree.replace(table.getId(),new Tuple<>(false,card));
        assert table != null;

        Platform.runLater(()->{
            create_card_from_text(table, card);
            table.getChildren().add(b);
        });
    }

    private void getCardFromTable(MouseEvent e){

        StackPane sp;
        Button source = (Button)e.getSource();
        StackPane parent = (StackPane) source.getParent();
        switch(parent.getId()){
            case "stack_table1": sp = stack_table1;
                break;
            case "stack_table2": sp = stack_table2;
                break;
            case "stack_table3": sp = stack_table3;
                break;
            case "stack_table4": sp = stack_table4;
                break;
            case "stack_table5": sp = stack_table5;
                break;
            case "stack_table6": sp = stack_table6;
                break;
            case "stack_table7": sp = stack_table7;
                break;
            case "stack_table8": sp = stack_table8;
                break;
            case "stack_table9": sp = stack_table9;
                break;
            case "stack_table90": sp = stack_table90;
                break;
            default: sp = null;
                break;
        }

        StackPane hand;
        if(hand_StackPaneFree.entrySet().stream().anyMatch(set-> set.getValue().x)) {
            String freeStackPaneInHand = hand_StackPaneFree.entrySet().stream().filter(set-> set.getValue().x).findFirst().get().getKey();
            switch(freeStackPaneInHand){
                case "stack_hand1": hand = stack_hand1;
                    break;
                case "stack_hand2": hand = stack_hand2;
                    break;
                case "stack_hand3": hand = stack_hand3;
                    break;
                case "stack_hand4": hand = stack_hand4;
                    break;
                case "stack_hand5": hand = stack_hand5;
                    break;
                case "stack_hand6": hand = stack_hand6;
                    break;
                case "stack_hand7": hand = stack_hand7;
                    break;
                case "stack_hand8": hand = stack_hand8;
                    break;
                default: hand = null;
                    break;
            }

            Button b = new Button();
            b.getStyleClass().add("button_card_in_hand");
            b.setOnMouseClicked(f-> dropCard(f));

            hand.getChildren().addAll(sp.getChildren().filtered(child->child instanceof Canvas || child instanceof Label));
            hand.getChildren().add(b);

            SimplifiedCard card = table_StackPaneFree.get(sp.getId()).y;
            player.simhand.add(card);

            hand_StackPaneFree.replace(freeStackPaneInHand,new Tuple<>(false,card));
            client.sendMessage("GOT_CARD_FROM_TABLE#" + card.id);
            //table_StackPaneFree.replace(sp.getId(),new Tuple<>(true,null));
        }
    }

    public void putCardToHand(SimplifiedCard c) {
        StackPane hand;
        if (hand_StackPaneFree.entrySet().stream().anyMatch(set -> set.getValue().x)) {
            String freeStackPaneInHand = hand_StackPaneFree.entrySet().stream().filter(set -> set.getValue().x).findFirst().get().getKey();
            switch (freeStackPaneInHand) {
                case "stack_hand1":
                    hand = stack_hand1;
                    break;
                case "stack_hand2":
                    hand = stack_hand2;
                    break;
                case "stack_hand3":
                    hand = stack_hand3;
                    break;
                case "stack_hand4":
                    hand = stack_hand4;
                    break;
                case "stack_hand5":
                    hand = stack_hand5;
                    break;
                case "stack_hand6":
                    hand = stack_hand6;
                    break;
                case "stack_hand7":
                    hand = stack_hand7;
                    break;
                case "stack_hand8":
                    hand = stack_hand8;
                    break;
                default:
                    hand = null;
                    break;
            }

            Button b = new Button();
            b.getStyleClass().add("button_card_in_hand");
            b.setOnMouseClicked(f -> dropCard(f));

            hand_StackPaneFree.put(hand.getId(), new Tuple<>(false, c));

            Platform.runLater(()->{
                create_card_from_text(hand, c);
                hand.getChildren().add(b);
            });
        }
    }

    public void removeCardFromTable(int id){
        StackPane sp;
        String whereToRemoveCard = table_StackPaneFree.entrySet().stream().filter(entry -> entry.getValue().y.id==id).findAny().get().getKey();
        switch(whereToRemoveCard){
            case "stack_table1": sp = stack_table1;
                break;
            case "stack_table2": sp = stack_table2;
                break;
            case "stack_table3": sp = stack_table3;
                break;
            case "stack_table4": sp = stack_table4;
                break;
            case "stack_table5": sp = stack_table5;
                break;
            case "stack_table6": sp = stack_table6;
                break;
            case "stack_table7": sp = stack_table7;
                break;
            case "stack_table8": sp = stack_table8;
                break;
            case "stack_table9": sp = stack_table9;
                break;
            case "stack_table90": sp = stack_table90;
                break;
            default: sp = null;
                break;
        }
        Platform.runLater(()->{
            sp.getChildren().clear();
        });

        table_StackPaneFree.replace(whereToRemoveCard, new Tuple<>(true, null));
    }

    @FXML
    private void dropCard(MouseEvent e){

        Button source = (Button)e.getSource();
        StackPane parent = (StackPane) source.getParent();
        source.setDisable(true);
        SimplifiedCard card = hand_StackPaneFree.get(parent.getId()).y;
        player.simhand.remove(card);

        parent.getChildren().remove(0);
        parent.getChildren().remove(0);
        parent.getChildren().remove(0);
        hand_StackPaneFree.replace(parent.getId(),new Tuple<>(true,null));

        client.sendMessage("DROP_CARD#" + card.id);
    }

    private void show_dialog_HowManyPlayers(){
        ArrayList<String> howManyPlayers = new ArrayList<>(){{add("2");add("3");add("4");add("5");add("6");}};

        ChoiceDialog<String> dialog = new ChoiceDialog<>(howManyPlayers.get(0), howManyPlayers);
        dialog.setTitle("Server setup");
        dialog.setHeaderText("Number of players");
        dialog.setContentText("Choose number of players for your game:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            thisPlayerMaxPlayers = Integer.parseInt(result.get());
        }
    }

    private void show_dialog_InsertYourName(){
        TextInputDialog dialog = new TextInputDialog("your_name");
        dialog.setTitle("Player setup");
        dialog.setHeaderText("Your name");
        dialog.setContentText("Please enter your name:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            thisPlayername = result.get();

        }
    }
}
