package client;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Controller class for the the board vista. Holds all object necessary for drawing the board.
 * @author Tereza Miklóšová
 */
public class BoardController implements Initializable{

    /**
     * List containing all hand stack panes. This List represents the hand.
     */
    private  static List<StackPane> hand_StackPanes;

    /**
     * List containing all table stack panes. This List represents the table.
     */
    private static List<StackPane> table_StackPanes;

    /**
     * Label for turn rules shown above the deck image.
     */
    @FXML
    public Label label_turnRules;
    public Button button_scoreReveal;

    /**
     * Contains all players to draw their data on the labels. Used to iterate through to decide who is playing.
     */
    private ArrayList<SimplePlayer> players;

    /**
     * Client for communicating with the server.
     */
    private Client client;

    /**
     * Contains data about the player using this application.
     */
    private static Player player;

    /**
     * Checks the number of cards on the table.
     */
    private int numberOfCardsOnTable = 0;

    public void setPreviewScore(int previewScore) {
        this.previewScore = previewScore;
    }

    private int previewScore = -999;

    /**
     * Checks if the player got all the beginning 7 cards from the server. Until this is true,
     * the window wont be drawn.
     */
    public static AtomicBoolean gotAllCards = new AtomicBoolean(false);

    /**
     * Stack panes for hand holding image of the card and the button to put the card to table.
     * Together the 8 stack panes represent the hand of the player.
     * The hand is automatically sorted by the type of the card.
     */
    @FXML
    public StackPane stack_hand1, stack_hand2, stack_hand3, stack_hand4, stack_hand5, stack_hand6, stack_hand7, stack_hand8;

    /**
     * Stack panes holding card images and buttons for taking the card from the table to hand.
     * Together the 10 stack panes represent the table.
     * Cards are going to the panes from left to the right. First free gets filled.
     */
    @FXML
    public StackPane stack_table1, stack_table2, stack_table3, stack_table4, stack_table5, stack_table6, stack_table7, stack_table8, stack_table9, stack_table90, stack_deck;

    /**
     * Deck button covers the image of the card back and sends info to the server that the player wants to draw a card
     * from the deck.
     */
    @FXML
    private Button deck_Button;

    /**
     * Button back to menu appears only after the end of the game.
     */
    @FXML
    private Button button_backToMenu;

    /**
     * Labels for all possible players joining the game. They contain player's name and at the end of the game
     * also his score. The labels change background color depending on who is the current playing player.
     */
    @FXML
    private Label label_player1,label_player2, label_player3, label_player4, label_player5, label_player6;

    /**
     * A label with player's name. At the end of the game, the label holds text informing about the end of the game.
     */
    @FXML
    private Label label_score;

    /**
     * The maximum height of card text area for bonuses and penalties.
     */
    private final double MAX_TEXT_HEIGHT = 100.0;

    // map having name of component where it resides and <Free?, If not what card it contains>
    /**
     * Map of hand elements. Key is the name of the stack pane, value is a tuple where the first value is whether
     * the stack pane is free or not. The second value holds a simplified card object if the pane is not free.
     * Null otherwise.
     */
    private TreeMap<String, Tuple<Boolean, SimplifiedCard>> hand_StackPaneFree = new TreeMap<>();

    /**
     * Map of table elements. Key is the name of the stack pane, value is a tuple where the first value is whether
     * the stack pane is free or not. The second value holds a simplified card object if the pane is not free.
     * Null otherwise.
     */
    private TreeMap<String, Tuple<Boolean, SimplifiedCard>> table_StackPaneFree = new TreeMap<>();

    /**
     * @return {@link BoardController#hand_StackPaneFree}
     */
    public TreeMap<String, Tuple<Boolean, SimplifiedCard>> getHandStackPanes(){
        return this.hand_StackPaneFree;
    }

    /**
     * @return {@link BoardController#table_StackPaneFree}
     */
    public TreeMap<String, Tuple<Boolean, SimplifiedCard>> getTableStackPanes(){
        return this.table_StackPaneFree;
    }

    /**
     * @return {@link BoardController#client}
     */
    public Client getClient() {
        return client;
    }

    /**
     * @return {@link Player#getNumberOfAI()}
     */
    public int getNumberOfAI() {
        return player.getNumberOfAI();
    }

    /**
     * @return {@link BoardController#player}
     */
    public static Player getPlayer(){
        return player;
    }

    /**
     * Event handler fired when the user requests a previous vista.
     *
     * @param event the event that triggered the handler.
     */
    @FXML
    void backToMenu(ActionEvent event) {
        //client.sendMessage("ENDCONNECTION");
        /*
        try {
            client.s.close();
        } catch(IOException ex){
            ex.printStackTrace();
        }

         */
        SceneNavigator.loadVista(SceneNavigator.MENU);

    }


    /**
     * Method draws the card on the given StackPane by the information contained in SimplifiedCard object.
     * @param sp StackPane to put the drawn card into.
     * @param card Contains information about the data needed for drawing the card.
     */
    public void create_card_from_text(StackPane sp, SimplifiedCard card){

        // Getting the data for the card
        String id = String.valueOf(card.getId());
        String name = card.getName();
        String type = card.getType();
        String strength = String.valueOf(card.getStrength());
        String cardText = card.getAllText();

        // Creating the basic shape and holders for the graphics
        Canvas canvas_hand1 = new Canvas(150,200);
        GraphicsContext gc = canvas_hand1.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 150,200);

        //Decide the image address based on name of the card and draw it on the canvas
        String img_addr = BigSwitches.switchImage(Integer.parseInt(id));
        Image image1 = new Image(getClass().getResourceAsStream(img_addr));
        gc.drawImage(image1,0,20, 150.0,80.0);

        //Decide the color and String of type rectangle based on Type enum
        ArrayList<String> typeColorAndName = BigSwitches.switchType(BigSwitches.switchNameForType(type),
                player.getLocale());
        String color_hex = typeColorAndName.get(0);
        String type_name = typeColorAndName.get(1);


        //Make the type rectangle with LinearGradient fill
        Stop[] stops = new Stop[] { new Stop(0, Color.web(color_hex)), new Stop(0.5, Color.web("FFFFFF")),
                new Stop(1, Color.web(color_hex))};
        LinearGradient lg1 = new LinearGradient(0, 0, 1,0, true, CycleMethod.REFLECT, stops);
        gc.setFill(lg1);
        gc.fillRect(0 ,0,20,200);

        gc.setFill(Color.LIGHTGREY);
        gc.fillRect(0 ,0,150,20);
        gc.setFill(Color.IVORY);
        gc.fillOval(- 10 ,- 10,40,40);
        gc.setFill(Color.BLACK);

        gc.fillText(name, 35, 15);
        gc.strokeText(strength, 5, 15);

        //Set button over the canvas and style it
        Button b = new Button();
        b.getStyleClass().add("button_card_in_hand");
        b.setOnMouseClicked(this::dropCard);
        gc.setFill(Color.BLACK);

        //Rotated type text
        gc.rotate(-90);
        Text typeText = new Text(type_name);
        typeText.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        gc.fillText(type_name, -80, 14);

        // Restores the original orientation
        gc.restore();

        // Fit the text of the bonuses/penalties.
        final Label text = new Label();
        final double defaulFontSize = 10;
        text.setText(cardText);

        Label tempLabel = new Label(cardText);
        //maximum width of the text/label
        double MAX_TEXT_WIDTH = 115.0;
        tempLabel.setMaxWidth(MAX_TEXT_WIDTH);
        tempLabel.setTranslateY(50);
        tempLabel.setTranslateX(5);
        tempLabel.setWrapText(true);
        tempLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 10));

        // If the text is too big (exceedes the MAX_TEXT_HEIGHT), the font size is made smaller.
        ObjectProperty<Font> fontTracking = new SimpleObjectProperty<>(Font.font("Verdana", FontWeight.BOLD, defaulFontSize));
        text.fontProperty().bind(fontTracking);
        tempLabel.heightProperty().addListener((observableValue, number, newHeight) -> {
            double height = (double) newHeight;
            if (height > MAX_TEXT_HEIGHT) {
                double newFontSize = defaulFontSize * MAX_TEXT_HEIGHT / (height + 10);
                fontTracking.set(Font.font("Verdana", FontWeight.BOLD, newFontSize));
                tempLabel.setMaxHeight(MAX_TEXT_HEIGHT);
            }

        });

        // Position the text to be in the right place.
        text.setWrapText(true);
        text.setTranslateY(50);
        text.setTranslateX(5);
        text.setMaxSize(MAX_TEXT_WIDTH,MAX_TEXT_HEIGHT);
        text.setAlignment(Pos.CENTER);

        // Add new components on the stack pane
        sp.getChildren().addAll(tempLabel,canvas_hand1,text);
    }

    /**
     * Method for initializing the board. Contains all necessary preparations of the board and all its components.
     * @param url Url for initialization.
     * @param resourceBundle Resource bundle for UI texts.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Creation of player and client. Starts clients thread to listen commands and to send messages to server.
        player = new Player();
        client = new Client(player,this);
        Thread t = new Thread(client);
        t.start();

        // Loads collections for handling stack panes for hand and table
            hand_StackPanes = new ArrayList<>();
            hand_StackPanes.addAll(Arrays.asList(stack_hand1, stack_hand2, stack_hand3, stack_hand4, stack_hand5, stack_hand6, stack_hand7, stack_hand8));
            table_StackPanes = new ArrayList<>();
            table_StackPanes.addAll(Arrays.asList(stack_table1, stack_table2, stack_table3, stack_table4, stack_table5, stack_table6, stack_table7, stack_table8, stack_table9, stack_table90));

            for (StackPane s : table_StackPanes) {
                table_StackPaneFree.put(s.getId(), new Tuple<>(true, null));
            }
            deck_Button.setOnMouseClicked(e -> getCardFromDeck());
            deck_Button.setDisable(true);

            // Waits till the client gets 7 cards. Then the cards in hand are possible to create.
            boolean first = true;
            gotAllCards.set(false);
            while (!gotAllCards.get()) {
                if(first){
                    System.out.println("Waiting for server to get us all cards...");
                }
                first = false;
            }
            crate_hand_SimplifiedCards();
            // Unsee this button as we dont want to let people to the main menu during the game.
            button_backToMenu.setVisible(false);
            button_backToMenu.setOnAction(this::backToMenu);
            //Set the players name at the top of the screen.
        Platform.runLater(()-> label_score.setText(label_score.getText() + " " + player.getName()));
        setPreviewScoreButton();

    }

    private void setPreviewScoreButton(){
        button_scoreReveal.setOnMousePressed(event -> {
            // Request score from server
            if(previewScore == -999){
                client.sendMessage("COUNTSCORE");
            } else{
                button_scoreReveal.setText(String.valueOf(previewScore));
            }

        });
        button_scoreReveal.setOnMouseReleased(released -> {
            ResourceBundle rb = ResourceBundle.getBundle("client.UITexts", player.getLocale());
            button_scoreReveal.setText(rb.getString("revealscore"));
        });
        button_scoreReveal.setOnMouseEntered(event -> {
            // Request score from server
            if(previewScore == -999){
                client.sendMessage("COUNTSCORE");
            } else{
                Platform.runLater(()-> {
                    button_scoreReveal.setText(String.valueOf(previewScore));
                });
            }

        });
        button_scoreReveal.setOnMouseExited(released -> {
            ResourceBundle rb = ResourceBundle.getBundle("client.UITexts", player.getLocale());
                    Platform.runLater(()-> {
                        button_scoreReveal.setText(rb.getString("revealscore"));
                    });
        });
    }

    /**
     * Method for creating all cards in player's hand to stack panes.
     */
    private void crate_hand_SimplifiedCards(){
        Collections.sort(player.simhand);
            Iterator<SimplifiedCard> iter = player.simhand.iterator();
            for (StackPane sp : hand_StackPanes) {

                if (iter.hasNext()) {
                    SimplifiedCard card = iter.next();
                    create_card_from_text(sp, card);
                    Button b = new Button();
                    b.getStyleClass().add("button_card_in_hand");
                    b.setOnMouseClicked(this::dropCard);
                    sp.getChildren().add(b);
                    b.setDisable(true);
                    hand_StackPaneFree.put(sp.getId(), new Tuple<>(false, card));
                }
            }
        hand_StackPaneFree.put(stack_hand8.getId(),new Tuple<>(true,null));
    }

    /**
     * Sends message to server to give the player the first card from deck.
     * Disables the first action buttons and enables the second action buttons
     * as we already used our first action (draw from deck or take card from table).
     */
    public void getCardFromDeck(){
        client.sendMessage("GIVE_CARD_FROM_DECK");

        enableFirstActionButtons(false);
        enableSecondActionButtons(true);
    }

    /**
     * Puts a card to the table. Doing that based on the messages received from the server.
     * @param card Card to draw to the table.
     */
    public void putCardOnTable(SimplifiedCard card){
        StackPane table;
        // Find the first free stack pane on table and draw the card to it.
        String freeStackPaneOnTable = Objects.requireNonNull(table_StackPaneFree.entrySet().stream().filter(set -> set.getValue().x).findFirst().orElse(null)).getKey();
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

        // puts a button to the card so it can be taken from the table
        Button b = new Button();
        b.getStyleClass().add("button_card_on_board");
        b.setOnMouseClicked(this::getCardFromTable);
        b.setDisable(true);

        assert table != null;
        table_StackPaneFree.replace(table.getId(),new Tuple<>(false,card));

        numberOfCardsOnTable++;

        // Create the card and display the changes.
        Platform.runLater(()->{
            create_card_from_text(table, card);
            table.getChildren().add(b);
            //Lets change current player on labels
            setNextPlayer();
        });
    }

    /**
     * Get the card under the button to the hand.
     * @param e The event from the button click.
     */
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

        // Choose the stack pane in hand where the card should belong
        StackPane hand;
        if(hand_StackPaneFree.entrySet().stream().anyMatch(set-> set.getValue().x)) {
            String freeStackPaneInHand = Objects.requireNonNull(hand_StackPaneFree.entrySet().stream().filter(set -> set.getValue().x).findFirst().orElse(null)).getKey();
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
            b.setOnMouseClicked(this::dropCard);

            assert hand != null;
            assert sp != null;
            hand.getChildren().addAll(sp.getChildren().filtered(child->child instanceof Canvas || child instanceof Label));
            hand.getChildren().add(b);

            // Add the card to your hand
            SimplifiedCard card = table_StackPaneFree.get(sp.getId()).y;
            player.simhand.add(card);

            // Repaint cards so they are sorted in the hand
            repaintAllCards();

            // Send message to server that you got the card form table
            client.sendMessage("GOT_CARD_FROM_TABLE#" + card.getId());

            // Enable second action buttons and disable the first action buttons as we already made first action
            enableFirstActionButtons(false);
            enableSecondActionButtons(true);

        }
    }

    /**
     *     Repaints all cards in hand after some action that changes it. Before painting the cards are sorted by type.
     */
    private void repaintAllCards(){
        Platform.runLater(()-> {
            for (StackPane sp : hand_StackPanes) {
                sp.getChildren().clear();
            }
            Collections.sort(player.simhand);
            Iterator<SimplifiedCard> iter = player.simhand.iterator();
            for (StackPane sp : hand_StackPanes) {

                if (iter.hasNext()) {
                    SimplifiedCard card = iter.next();
                    create_card_from_text(sp, card);
                    Button b = new Button();
                    b.getStyleClass().add("button_card_in_hand");
                    b.setOnMouseClicked(this::dropCard);
                    sp.getChildren().add(b);
                    b.setDisable(true);
                    hand_StackPaneFree.put(sp.getId(), new Tuple<>(false, card));
                } else {
                    hand_StackPaneFree.put(sp.getId(), new Tuple<>(true, null));
                }
            }
        });

    }

    /**
     * Get the stack pane by its name.
     * @param name Name of the stack pane.
     * @return Object of the named stack pane.
     */
    public StackPane switchNameForStackPane(String name){
        StackPane freeStackPane;
        switch(name){
            case "stack_hand1": freeStackPane = stack_hand1;
                break;
            case "stack_hand2": freeStackPane = stack_hand2;
                break;
            case "stack_hand3": freeStackPane = stack_hand3;
                break;
            case "stack_hand4": freeStackPane = stack_hand4;
                break;
            case "stack_hand5": freeStackPane = stack_hand5;
                break;
            case "stack_hand6": freeStackPane = stack_hand6;
                break;
            case "stack_hand7": freeStackPane = stack_hand7;
                break;
            case "stack_hand8": freeStackPane = stack_hand8;
                break;
            default: freeStackPane = null;
                break;
        }
        return freeStackPane;
    }

    /**
     * Puts a new card in hand.
     */
    public void putCardToHand() {
        StackPane hand;
        if (hand_StackPaneFree.entrySet().stream().anyMatch(set -> set.getValue().x)) {
            String freeStackPaneInHand = Objects.requireNonNull(hand_StackPaneFree.entrySet().stream().filter(set -> set.getValue().x).findFirst().orElse(null)).getKey();
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
            b.setOnMouseClicked(this::dropCard);
            repaintAllCards();
            enableFirstActionButtons(false);
            enableSecondActionButtons(true);
            assert hand != null;
        }
    }

    /**
     * Removes a card from the table to be the same as the server has the table filled.
     * @param id The id of the card that should be removed from the table.
     */
    public void removeCardFromTable(int id){
        StackPane sp;
        String whereToRemoveCard = Objects.requireNonNull(table_StackPaneFree.entrySet().stream().filter(entry -> entry.getValue().y.getId() == id).findAny().orElse(null)).getKey();
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
        assert sp != null;
        Platform.runLater(()-> sp.getChildren().clear());
        numberOfCardsOnTable--;
        table_StackPaneFree.replace(whereToRemoveCard, new Tuple<>(true, null));
    }

    /**
     * Drops card from hand. Repaints all cards to reflect the change.
     * @param e The button event from clicking on it.
     */
    @FXML
    private void dropCard(MouseEvent e){

        Button source = (Button)e.getSource();
        StackPane parent = (StackPane) source.getParent();

        SimplifiedCard card = hand_StackPaneFree.get(parent.getId()).y;
        player.simhand.remove(card);

        parent.getChildren().remove(0);
        parent.getChildren().remove(0);
        parent.getChildren().remove(0);
        repaintAllCards();

        client.sendMessage("DROP_CARD#" + card.getId());

        enableSecondActionButtons(false);
        previewScore = -999;
    }

    private String localizeScore(String score){
        StringBuilder sb = new StringBuilder();
        ResourceBundle rb = ResourceBundle.getBundle("client.UITexts", player.getLocale());
        ResourceBundle nameBundle = ResourceBundle.getBundle("client.CardNames", player.getLocale());
        String[] cards = score.split("\n");
        if(cards.length > 1){
            for(int i = 0; i < cards.length; i++){

                String[] cardProperties = cards[i].split(";");
                String name = nameBundle.getString(cardProperties[0]);
                int tabCount = 3 - (name.length() / 8);
                String type = BigSwitches.switchTypeForName(Type.valueOf(cardProperties[1]), player.getLocale());
                if(cardProperties[2].startsWith("DELETED")){
                    sb.append(rb.getString("cardcapital")).append(" ").append(name).append(" (").append(type).
                            append(") ").append(rb.getString("deleted")).append(".");
                } else{
                    String strength = cardProperties[2];
                    sb.append(rb.getString("cardcapital")).append(" ").append(name).append(" ").
                            append(" (").append(type).append(") ").
                            append(rb.getString("contributed")).append(" ").append(strength).append(".");

                    if(cardProperties.length > 3){
                        sb.append("\t".repeat(Math.max(0, tabCount)));
                        if(cardProperties[3].startsWith("BONUS")){
                            sb.append(rb.getString("bonus")).append(": +");
                            String[] bonus = cardProperties[3].split(":");
                            sb.append(bonus[1]).append("\t");
                        }
                        if(cardProperties[3].startsWith("MALUS")){
                            sb.append(rb.getString("malus")).append(": ");
                            String[] malus = cardProperties[3].split(":");
                            sb.append(malus[1]);
                        }
                        if(cardProperties.length > 4){
                            sb.append(rb.getString("malus")).append(": ");
                            String[] malus = cardProperties[4].split(":");
                            sb.append(malus[1]);
                        }

                    }
                }

                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * At the end of the game changes the colors of the labels and its text to fit the winning statistics.
     * @param label Which label should be repainted.
     * @param toAdd Text of the score to add to the label.
     * @return String with the name and score of the player on the label.
     */
    public TextFlow changeLabel(Label label, String toAdd, String score){
        String oldText = label.getText();
        Tooltip tt = new Tooltip();
        String textForLabelTooltip = localizeScore(score);
        Hyperlink showDetails = new Hyperlink(" Detail ");
        showDetails.setOnAction(event -> {
            Platform.runLater(()-> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(oldText);
                alert.setHeaderText(textForLabelTooltip);
                alert.showAndWait();
            });
        });
        tt.setText(textForLabelTooltip);
        label.setTooltip(tt);
        Platform.runLater(()-> {
            label.setText(oldText + "\n" + toAdd);
            if(toAdd.startsWith("1")){
                label.getStyleClass().add("label_playerInGameGold");
            } else if(toAdd.startsWith("2")){
                label.getStyleClass().add("label_playerInGameSilver");
            } else if(toAdd.startsWith("3")){
                label.getStyleClass().add("label_playerInGameBronze");
            } else{
                label.getStyleClass().clear();
                label.getStyleClass().add("label_playerInGame");
            }
        });
        String retValue;
        retValue = toAdd.split("(\\) )")[0] + ") " + oldText + ", " + toAdd.split("(\\) )")[1];
        System.out.println("will be in row" + retValue);
        return new TextFlow(new Text(retValue), showDetails);
    }

    public String gatherLadder(Label label, String toAdd){
        String oldText = label.getText();
        System.out.println("old text: " + oldText);
        String retValue;
        retValue = toAdd.split("(\\) )")[0] + ") " + oldText.split("\n")[0] + ", " + toAdd.split("(\\) )")[1];
        System.out.println("retvalue: " + retValue);
        return retValue;
    }

    /**
     * Processes the information from the server about scores of the players. Prints a dialogue
     * window about the statistics. Changes the texts and colors of the labels in the process.
     * @param names Names of the players that should have their scores changed.
     */
    public void buildPlayerScores(String[] scores, String[] names){
        ArrayList<TextFlow> scoreTable = new ArrayList<>();
        ArrayList<String> ladder = new ArrayList<>();
        ResourceBundle rb = ResourceBundle.getBundle("client.UITexts", player.getLocale());
        if(names.length > 0){
            scoreTable.add(changeLabel(label_player1,names[0],scores[0]));
            ladder.add(gatherLadder(label_player1,names[0]));
        }
        if(names.length > 1){
            scoreTable.add(changeLabel(label_player2,names[1],scores[1]));
            ladder.add(gatherLadder(label_player2,names[1]));
        }
        if(names.length > 2){
            scoreTable.add(changeLabel(label_player3,names[2],scores[2]));
            ladder.add(gatherLadder(label_player3,names[2]));
        }
        if(names.length > 3){
            scoreTable.add(changeLabel(label_player4,names[3],scores[3]));
            ladder.add(gatherLadder(label_player4,names[3]));
        }
        if(names.length > 4){
            scoreTable.add(changeLabel(label_player5,names[4],scores[4]));
            ladder.add(gatherLadder(label_player5,names[4]));
        }
        if(names.length > 5){
            scoreTable.add(changeLabel(label_player6,names[5],scores[5]));
            ladder.add(gatherLadder(label_player6,names[5]));
        }
        int rank = Integer.parseInt(names[0].split("\\)")[0]);

        String position;
        switch(rank){
            case 1: position = rb.getString("board_won");
            break;
            case 2: position = rb.getString("board_second");
            break;
            case 3: position = rb.getString("board_third");
            break;
            case 4: position = rb.getString("board_fourth");
            break;
            case 5: position = rb.getString("board_fifth");
            break;
            default: position = rb.getString("board_sixth");
        }
        int score = Integer.parseInt(names[0].split("(\\) )")[1]);
        //scoreTable.forEach(System.out::println);

        Collections.sort(ladder);
        TextFlow allFlowTexts = new TextFlow();
        String headerText = rb.getString("board_gz") + " " + player.getName() + "! " +
                position + " " + rb.getString("board_scoreof") + " " + score + " " +
                rb.getString("board_points") + "\n\n" + rb.getString("board_scoretable") +
                "\n";
        allFlowTexts.getChildren().add(new Text(headerText));
        int pos = 1;
        for (String s: ladder){
            System.out.println("inside loop with ladder children size" + scoreTable.get(0).getChildren().size()+ "s: " + s);

            int finalPos = pos;
            for(TextFlow tf : scoreTable){
                System.out.println(tf.getChildren().get(0).toString());
            }
            TextFlow tf = scoreTable.stream().filter(textflow ->((Text) textflow.getChildren().get(0)).getText().startsWith(String.valueOf(finalPos))).findAny().get();
                allFlowTexts.getChildren().add(tf);
                allFlowTexts.getChildren().add(new Text("\n"));
                pos++;
        }
        // Print an alert window with the final statistics.
        Platform.runLater(()-> {
            button_backToMenu.setVisible(true);
            button_backToMenu.setDisable(false);
            button_scoreReveal.setVisible(false);
            button_scoreReveal.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(rb.getString("board_finalscore"));

            HBox hbox = new HBox();
            HBox.setMargin(allFlowTexts, new Insets(10,10,10,10));
            hbox.setPadding(Insets.EMPTY);
            hbox.getChildren().addAll(allFlowTexts,new ImageView(this.getClass().getResource("graphics/medal.png").toString()));
            alert.getDialogPane().setHeader(hbox);
            alert.setContentText(rb.getString("board_compare"));
            //alert.setGraphic();

            alert.showAndWait();
        });
    }

    /**
     * When a connection error happens, this method prints an alert.
     */
    public void disconnectedFromServer(){
        ResourceBundle rb = ResourceBundle.getBundle("client.UITexts", player.getLocale());
        Platform.runLater(()-> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(rb.getString("board_conerror"));
            alert.setHeaderText(rb.getString("board_conerrortext1")  + " " + player.getName() + "!\n\n" + rb.getString("board_conerrortext2") );
            alert.setContentText(rb.getString("board_errordismiss"));

            Platform.exit();
            System.exit(1);
        });
    }

    /**
     * Method builds player labels by the information biven by the server to the client.
     * Method build the exact number of labels needed and fills them with player names.
     * @param names Player names in order from this player. Starting player has a special tag before its name.
     */
    public void buildPlayerLabels(String[] names){
        players = new ArrayList<>();
        if(names.length > 0){
            Platform.runLater(()-> {
                String name;
                if(names[0].startsWith("$&$START$&$")){
                    name = names[0].substring(11);
                    label_player1.getStyleClass().add("label_playerInGameActive");
                    players.add(new SimplePlayer(name, true));
                    label_player1.setText(name);
                    deck_Button.setDisable(false);
                } else{
                    name = names[0];
                    label_player1.getStyleClass().add("label_playerInGame");
                    label_player1.setText(name);
                    players.add(new SimplePlayer(name, false));
                }
            });
        }
        if(names.length > 1){
            Platform.runLater(()-> {
                String name;
                if(names[1].startsWith("$&$START$&$")){
                    name = names[1].substring(11);
                    label_player2.getStyleClass().add("label_playerInGameActive");
                    players.add(new SimplePlayer(name, true));
                    label_player2.setText(name);
                } else{
                    name = names[1];
                    label_player2.getStyleClass().add("label_playerInGame");
                    label_player2.setText(name);
                    players.add(new SimplePlayer(name, false));
                }
            });
        }
        if(names.length > 2){
            Platform.runLater(()-> {
                String name;
                if(names[2].startsWith("$&$START$&$")){
                    name = names[2].substring(11);
                    label_player3.getStyleClass().add("label_playerInGameActive");
                    players.add(new SimplePlayer(name, true));
                    label_player3.setText(name);
                } else{
                    name = names[2];
                    label_player3.getStyleClass().add("label_playerInGame");
                    label_player3.setText(name);
                    players.add(new SimplePlayer(name, false));
                }
            });
        }
        if(names.length > 3){
            Platform.runLater(()-> {
                String name;
                if(names[3].startsWith("$&$START$&$")){
                    name = names[3].substring(11);
                    label_player4.getStyleClass().add("label_playerInGameActive");
                    players.add(new SimplePlayer(name, true));
                    label_player4.setText(name);
                } else{
                    name = names[3];
                    label_player4.getStyleClass().add("label_playerInGame");
                    label_player4.setText(name);
                    players.add(new SimplePlayer(name, false));
                }
            });
        }
        if(names.length > 4){
            Platform.runLater(()-> {
                String name;
                if(names[4].startsWith("$&$START$&$")){
                    name = names[4].substring(11);
                    label_player5.getStyleClass().add("label_playerInGameActive");
                    players.add(new SimplePlayer(name, true));
                    label_player5.setText(name);
                } else{
                    name = names[4];
                    label_player5.getStyleClass().add("label_playerInGame");
                    label_player5.setText(name);
                    players.add(new SimplePlayer(name, false));
                }
            });
        }
        if(names.length > 5){
            Platform.runLater(()-> {
                String name;
                if(names[5].startsWith("$&$START$&$")){
                    name = names[5].substring(11);
                    label_player6.getStyleClass().add("label_playerInGameActive");
                    players.add(new SimplePlayer(name, true));
                    label_player6.setText(name);
                } else{
                    name = names[5];
                    label_player6.getStyleClass().add("label_playerInGame");
                    label_player6.setText(name);
                    players.add(new SimplePlayer(name, false));
                }
            });
        }
        System.out.println("Client got player names!");
    }


    /**
     * This method enables or disables the buttons for first action in the round. (Cards on the table or the deck).
     * @param enable True if to enable. False if to disable.
     */
    public void enableFirstActionButtons(boolean enable){
        deck_Button.setDisable(!enable);
        table_StackPaneFree.entrySet().stream().filter(set-> !set.getValue().x).forEach(entrySet -> enableStackPaneString(entrySet.getKey(),enable));
    }

    /**
     * Changes the appearance of the stack pane string.
     * @param stackPaneName Stack pane in which to change the appearance.
     * @param enable True if enable the text to full opacity. False if the text should be less visible.
     */
    private void enableStackPaneString(String stackPaneName, boolean enable){
        StackPane thePane =  table_StackPanes.stream().filter(pane -> pane.getId().equals(stackPaneName)).findFirst().orElse(null);
        assert thePane != null;
        if(thePane.getChildren().size() != 0 && thePane.getChildren().size() != 1){
            thePane.getChildren().get(thePane.getChildren().size() - 1).setDisable(!enable);
        }
    }

    /**
     * Enables or disables the buttons for cards in hand.
     * @param enable True if to enable. False if to disable.
     */
    public void enableSecondActionButtons(boolean enable){
        hand_StackPanes.forEach(pane -> Platform.runLater(()-> {
            if(pane.getChildren().size() != 0)
                pane.getChildren().get(pane.getChildren().size() - 1).setDisable(!enable);
        }));
    }

    /**
     * Changes the active player label. If the active player is the first one (us), enables the first action buttons.
     */
    private void setNextPlayer(){
        if(numberOfCardsOnTable < 10) {
            int numberOfPlayers = players.size();
            int maxIndex = numberOfPlayers - 1;
            SimplePlayer activePlayer = players.stream().filter(SimplePlayer::getPlaying).findAny().orElse(null);
            int activeIndex = players.indexOf(activePlayer);
            int newActiveIndex = 0;
            if (activeIndex + 1 <= maxIndex) {
                newActiveIndex = activeIndex + 1;
            }
            SimplePlayer newActivePlayer = players.get(newActiveIndex);
            assert activePlayer != null;
            activePlayer.setPlaying(false);
            newActivePlayer.setPlaying(true);
            changeActiveLabel(newActiveIndex);
            // newActivePlayer is this client (in players array its index 0

            if(activeIndex == 0){
                Platform.runLater(() -> {
                    enableFirstActionButtons(false);
                    enableSecondActionButtons(false);
                });
            }
            if (newActiveIndex == 0) {
                Platform.runLater(() -> enableFirstActionButtons(true));
            }
        }
    }

    /**
     * Changes the active label from one to another. Repaints the labels.
     * @param indexOfPlayerNew The index of new active player.
     */
    private void changeActiveLabel(int indexOfPlayerNew){
        Label oldIndex, newIndex;
        switch(indexOfPlayerNew){
            case 0: newIndex = label_player1;
                    switch(players.size()){
                        case 2: oldIndex = label_player2;
                            break;
                        case 3: oldIndex = label_player3;
                            break;
                        case 4: oldIndex = label_player4;
                            break;
                        case 5: oldIndex = label_player5;
                            break;
                        case 6: oldIndex = label_player6;
                            break;
                        default: oldIndex = newIndex;
                    }
                break;
            case 1: newIndex = label_player2;
                    oldIndex = label_player1;
                break;
            case 2: newIndex = label_player3;
                    oldIndex = label_player2;
                break;
            case 3: newIndex = label_player4;
                    oldIndex = label_player3;
                break;
            case 4: newIndex = label_player5;
                    oldIndex = label_player4;
                break;
            case 5: newIndex = label_player6;
                    oldIndex = label_player5;
                break;
            default:    newIndex = label_player1;
                        oldIndex = label_player1;
        }
        oldIndex.getStyleClass().clear();
        oldIndex.getStyleClass().add("label_playerInGame");
        newIndex.getStyleClass().clear();
        newIndex.getStyleClass().add("label_playerInGameActive");
    }

    /**
     * Puts an informative text on a label that the game has already ended.
     */
    public void putEndGameTextOnLabel(){
        ResourceBundle rb = ResourceBundle.getBundle("client.UITexts", player.getLocale());
        Platform.runLater(()-> {
            label_score.setText(rb.getString("board_endgamealert"));
            enableSecondActionButtons(false);
            enableFirstActionButtons(false);
        });
    }
}
