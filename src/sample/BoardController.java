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
import javafx.scene.layout.Border;
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
    public static List<Label> labels_players;
    private ArrayList<SimplePlayer> players;
    public Client client;
    private int round = 0;
    private Random randomGenerator;
    public static Player player;
    private int numberOfCardsOnTable = 0;

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
    private Label label_player1,label_player2, label_player3, label_player4, label_player5, label_player6;

    @FXML
    private Label label_score;

    @FXML
    private HBox hand_HBox;

    public static ArrayList<Card> cardsOnTable;

    public static Deck deck;

    private String[] playerNames;

    private String currentPlayer;

    // map having name of component where it resides and <Free?, If not what card it contains>
    public TreeMap<String, Tuple<Boolean, SimplifiedCard>> hand_StackPaneFree = new TreeMap<>();
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
        text.setTranslateX(5);
        text.setMaxSize(115,150);
        text.setAlignment(Pos.CENTER);
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 10));

        sp.getChildren().addAll(canvas_hand1,text);
        // disable the button
        //sp.getChildren().get(2).setDisable(true);
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
        deck_Button.setDisable(true);

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
                    Button b = new Button();
                    b.getStyleClass().add("button_card_in_hand");
                    b.setOnMouseClicked(f-> dropCard(f));
                    sp.getChildren().add(b);
                    b.setDisable(true);
                    hand_StackPaneFree.put(sp.getId(), new Tuple<>(false, card));
                    //System.out.println("Number of components in stack pane " + sp.getChildren().size());
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

        enableFirstActionButtons(false);
        enableSecondActionButtons(true);
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
        b.setDisable(true);

        table_StackPaneFree.replace(table.getId(),new Tuple<>(false,card));
        assert table != null;

        numberOfCardsOnTable++;


        Platform.runLater(()->{
            create_card_from_text(table, card);
            table.getChildren().add(b);
            //Lets change current player on labels
            setNextPlayer();
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

            enableFirstActionButtons(false);
            enableSecondActionButtons(true);

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
        System.out.println("Children on stack table: " + sp.getChildren().size());
        Platform.runLater(()->{
            sp.getChildren().clear();
        });
        numberOfCardsOnTable--;
        table_StackPaneFree.replace(whereToRemoveCard, new Tuple<>(true, null));
    }

    @FXML
    private void dropCard(MouseEvent e){

        Button source = (Button)e.getSource();
        StackPane parent = (StackPane) source.getParent();

        SimplifiedCard card = hand_StackPaneFree.get(parent.getId()).y;
        player.simhand.remove(card);

        parent.getChildren().remove(0);
        parent.getChildren().remove(0);
        parent.getChildren().remove(0);
        hand_StackPaneFree.replace(parent.getId(),new Tuple<>(true,null));

        client.sendMessage("DROP_CARD#" + card.id);

        enableSecondActionButtons(false);
    }

    private void show_dialog_HowManyPlayers(){
        ArrayList<String> howManyPlayers = new ArrayList<>(){{add("1");add("2");add("3");add("4");add("5");add("6");}};

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

    public void buildPlayerScores(String[] names){
        System.out.println("Building player scores");
        if(names.length > 0){
            Platform.runLater(()-> {
                String oldText = label_player1.getText();
                label_player1.setText(oldText + "\n" + names[0]);
                if(names[0].startsWith("1")){

                } else if(names[0].startsWith("2")){

                } else if(names[0].startsWith("3")){

                }
            });
        }
    }


    public void buildPlayerLabels(String[] names){
        System.out.println("build player names start");
        playerNames = names;
        players = new ArrayList<>();
        System.out.println("players array number of elements: " + players.size());
        if(names.length > 0){
            Platform.runLater(()-> {
                String name;
                boolean starting;
                System.out.println("build player names 0");
                System.out.println(names[0]);

                if(names[0].startsWith("$&$START$&$")){
                    //TODO:
                    name = names[0].substring(11);
                    label_player1.getStyleClass().add("label_playerInGameActive");
                    players.add(new SimplePlayer(name, true));
                    label_player1.setText(name);
                    yourTurn = true;
                    //TODO : Zapnout karty a jedem
                    deck_Button.setDisable(false);
                } else{
                    name = names[0];
                    label_player1.getStyleClass().add("label_playerInGame");
                    label_player1.setText(name);
                    players.add(new SimplePlayer(name, false));
                    yourTurn = false;
                }
                System.out.println("players array number of elements after loading elements to it: " + players.size());

            });

        }
        if(names.length > 1){
            Platform.runLater(()-> {
                label_player2.setText(names[1]);
                label_player2.getStyleClass().add("label_playerInGame");
            });
        }
        if(names.length > 2){
            Platform.runLater(()-> {
                label_player3.setText(names[2]);
                label_player3.getStyleClass().add("label_playerInGame");
            });
        }
        if(names.length > 3){
            Platform.runLater(()-> {
                label_player4.setText(names[3]);
                label_player4.getStyleClass().add("label_playerInGame");
            });
        }
        if(names.length > 4){
            Platform.runLater(()-> {
                label_player5.setText(names[4]);
                label_player5.getStyleClass().add("label_playerInGame");
            });
        }
        if(names.length > 5){
            Platform.runLater(()-> {
                label_player6.setText(names[5]);
                label_player6.getStyleClass().add("label_playerInGame");
            });
        }



        System.out.println("Client got player names!!! SUCCESS");
    }

    public void enableFirstActionButtons(boolean enable){
        deck_Button.setDisable(!enable);
        table_StackPaneFree.entrySet().stream().filter(set->set.getValue().x == false).forEach(entrySet -> enableStackPaneString(entrySet.getKey(),enable));
    }

    private void enableStackPaneString(String stackPaneName, boolean enable){

        StackPane thePane =  table_StackPanes.stream().filter(pane -> pane.getId().equals(stackPaneName)).findFirst().get();
        System.out.println("StackPane enabling: Name: " + stackPaneName + " and number of children: " + thePane.getChildren().size() + " and we should disable index 2");
        if(thePane.getChildren().size() != 0 && thePane.getChildren().size() != 1){
            thePane.getChildren().get(2).setDisable(!enable);
        }
    }

    public void enableSecondActionButtons(boolean enable){
        hand_StackPanes.forEach(pane -> {
            int numberOfChildren = pane.getChildren().size();
            System.out.println("This pane is being enabled: " + pane.getId());
            Platform.runLater(()-> {
                if(pane.getChildren().size() != 0)
                    pane.getChildren().get(2).setDisable(!enable);
            });
            });
    }

    private void setNextPlayer(){
        if(numberOfCardsOnTable < 10) {
            int numberOfPlayers = players.size();
            int maxIndex = numberOfPlayers - 1;
            SimplePlayer activePlayer = players.stream().filter(player -> player.getPlaying() == true).findAny().get();
            int activeIndex = players.indexOf(activePlayer);
            System.out.println("Active index: " + activeIndex + " , max index: " + maxIndex + "active++: " + activeIndex++ + " NUMBER OF PLAYERS ELEMENTS: " + numberOfPlayers);
            int newActiveIndex = 0;
            if (activeIndex + 1 <= maxIndex) {
                newActiveIndex = activeIndex + 1;
            }
            SimplePlayer newActivePlayer = players.get(newActiveIndex);
            activePlayer.setPlaying(false);
            newActivePlayer.setPlaying(true);
            round++;
            System.out.println("ROUND: " + round + " , Next Active player is: " + newActivePlayer.getName() + "Index of next player: " + newActiveIndex);
            // newActivePlayer is this client (in players array its index 0
            if (newActiveIndex == 0) {
                Platform.runLater(() -> {
                    enableFirstActionButtons(true);
                    System.out.println("After enabling buttons, cuz Im new active player");
                });
            }
        }
    }

    public void putEndGameTextOnLabel(){
        Platform.runLater(()-> {
            label_score.setText("10 cards are on the table! The score of all players will now be counted. When the counting is finished, you will see the score on the panel on the right!");
            enableSecondActionButtons(false);
            enableFirstActionButtons(false);
        });
    }
}
