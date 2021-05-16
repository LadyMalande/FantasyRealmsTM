package client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.net.URL;
import java.util.*;


/**
 * Controller class for the second vista.
 */
public class BoardController implements Initializable{

    private String thisPlayername;
    private int thisPlayerMaxPlayers;
    public  static List<StackPane> hand_StackPanes;
    public static List<StackPane> table_StackPanes;
    private ArrayList<SimplePlayer> players;
    public Client client;
    private int round = 0;
    public static Player player;
    private int numberOfCardsOnTable = 0;

    public Player getPlayer(){
        return player;
    }

    public boolean yourTurn = true;
    public static boolean gotAllCards = false;
    @FXML
    public StackPane stack_hand1, stack_hand2, stack_hand3, stack_hand4, stack_hand5, stack_hand6, stack_hand7, stack_hand8;

    @FXML
    public StackPane stack_table1, stack_table2, stack_table3, stack_table4, stack_table5, stack_table6, stack_table7, stack_table8, stack_table9, stack_table90, stack_deck;

    @FXML
    private Button deck_Button, button_backToMenu;

    @FXML
    private Label label_player1,label_player2, label_player3, label_player4, label_player5, label_player6;

    @FXML
    private Label label_score, label_turnRules;

    //maximum width of the text/label
    private final double MAX_TEXT_WIDTH = 115;
    //default (nonscaled) font size of the text/label
    private final double defaultFontSize = 12;
    private final Font defaultFont = Font.font(defaultFontSize);

    public static boolean randomDeck;

    public static ArrayList<Card> cardsOnTable;

    // map having name of component where it resides and <Free?, If not what card it contains>
    public TreeMap<String, Tuple<Boolean, SimplifiedCard>> hand_StackPaneFree = new TreeMap<>();
    public TreeMap<String, Tuple<Boolean, SimplifiedCard>> table_StackPaneFree = new TreeMap<>();

    /**
     * Event handler fired when the user requests a previous vista.
     *
     * @param event the event that triggered the handler.
     */
    @FXML
    void backToMenu(ActionEvent event) {
        SceneNavigator.loadVista(SceneNavigator.MENU);
    }



    public void create_card_from_text(StackPane sp, SimplifiedCard card){

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
        gc.fillOval(- 10 ,- 10,40,40);
        gc.setFill(Color.BLACK);

        gc.fillText(name, 35, 15);
        gc.fillText(strength, 5, 15);

        //Set button over the canvas and style it
        Button b = new Button();
        b.getStyleClass().add("button_card_in_hand");
        b.setOnMouseClicked(this::dropCard);
        gc.setFill(Color.BLACK);

        //Rotated type text
        gc.rotate(-90);
        gc.fillText(type_name, -80, 14);
        gc.restore();

        cardText = cardText.replace("MALUS\n", "");
        final Label text = new Label( cardText);

        Platform.runLater( () ->
        {
            Double fontSize = text.getFont().getSize();
            //String clippedText = computeClippedText( text.getFont(), text.getText(), text.getWidth(), text.getTextOverrun(), text.getEllipsisString() );
            Font newFont = text.getFont();
            text.setWrapText(true);

            while ( text.getHeight() > 100)
            {

                fontSize = fontSize - 0.05;
                newFont = Font.font( text.getFont().getFamily(), fontSize );

            }

            text.setFont( newFont );
        } );



       // Label text = new Label(cardText);

        text.setTranslateY(50);
        text.setTranslateX(5);
        text.setMaxSize(115,100);
        text.setAlignment(Pos.CENTER);
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 10));

        sp.getChildren().addAll(canvas_hand1,text);
/*
        label.setWrapText(true);
        label.setTranslateY(50);
        label.setTranslateX(5);
        label.setMaxSize(115,100);
        label.setAlignment(Pos.CENTER);
        sp.getChildren().addAll(canvas_hand1,label); */
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
        label_turnRules.setText("1) Take card from the table or the deck \n2) Drop one card from your hand");
        button_backToMenu.setVisible(false);
        button_backToMenu.setOnAction(this::backToMenu);
        //hand_Buttons.forEach(button -> button.setOnMouseClicked(click -> dropCard(click)));
        //play();
    }

    private void crate_hand_SimplifiedCards(){
        System.out.println("In create_hand:Simplified cards");
        Collections.sort(player.simhand);
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

            repaintAllCards();

            //hand_StackPaneFree.replace(freeStackPaneInHand,new Tuple<>(false,card));
            client.sendMessage("GOT_CARD_FROM_TABLE#" + card.id);

            enableFirstActionButtons(false);
            enableSecondActionButtons(true);

            //table_StackPaneFree.replace(sp.getId(),new Tuple<>(true,null));
        }
    }

    private void repaintAllCards(){
        System.out.println("Deleting canvases from hand StackPanes...");
        Platform.runLater(()-> {
            for (StackPane sp : hand_StackPanes) {
                sp.getChildren().clear();
            }
            Collections.sort(player.simhand);
            Iterator<SimplifiedCard> iter = player.simhand.iterator();
            System.out.println("Repainting cards...");
            for (StackPane sp : hand_StackPanes) {

                if (iter.hasNext()) {
                    SimplifiedCard card = iter.next();
                    create_card_from_text(sp, card);
                    Button b = new Button();
                    b.getStyleClass().add("button_card_in_hand");
                    b.setOnMouseClicked(f -> dropCard(f));
                    sp.getChildren().add(b);
                    b.setDisable(true);
                    hand_StackPaneFree.put(sp.getId(), new Tuple<>(false, card));
                    //System.out.println("Number of components in stack pane " + sp.getChildren().size());
                } else {
                    hand_StackPaneFree.put(sp.getId(), new Tuple<>(true, null));
                }
            }
        });

    }

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
            b.setOnMouseClicked(this::dropCard);
            repaintAllCards();
            enableFirstActionButtons(false);
            enableSecondActionButtons(true);
            assert hand != null;
            //hand_StackPaneFree.put(hand.getId(), new Tuple<>(false, c));
/*
            Platform.runLater(()->{
                create_card_from_text(hand, c);
                hand.getChildren().add(b);
            });

 */
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
        repaintAllCards();
        //hand_StackPaneFree.replace(parent.getId(),new Tuple<>(true,null));

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
        } else{
            SceneNavigator.loadVista(SceneNavigator.MENU);
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
            Platform.runLater(()-> label_score.setText("Your name is: " + thisPlayername));
        }
        else{
            SceneNavigator.loadVista(SceneNavigator.MENU);
        }
    }

    public String changeLabel(Label label, String toAdd){
        String oldText = label.getText();
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
        String retValue = new String();
        retValue = toAdd.split("(\\) )")[0] + ") " + oldText + ", " + toAdd.split("(\\) )")[1];
        return retValue;
    }

    public void buildPlayerScores(String[] names){
        ArrayList<String> scoreTable = new ArrayList<>();

        System.out.println("names[0]: " + names[0]);
        System.out.println("Building player scores");
        if(names.length > 0){
            scoreTable.add(changeLabel(label_player1,names[0]));
        }
        if(names.length > 1){
            scoreTable.add(changeLabel(label_player2,names[1]));
        }
        if(names.length > 2){
            scoreTable.add(changeLabel(label_player3,names[2]));
        }
        if(names.length > 3){
            scoreTable.add(changeLabel(label_player4,names[3]));
        }
        if(names.length > 4){
            scoreTable.add(changeLabel(label_player5,names[4]));
        }
        if(names.length > 5){
            scoreTable.add(changeLabel(label_player6,names[5]));
        }
        int rank = Integer.parseInt(names[0].split("\\)")[0]);

        System.out.println("names[0] se snazim splitnout : " + names[0]);
        String position;
        switch(rank){
            case 1: position = "won the game";
            break;
            case 2: position = "are second";
            break;
            case 3: position = "are third";
            break;
            case 4: position = "are fourth";
            break;
            case 5: position = "are fifth";
            break;
            default: position = "are sixth";
        }
        int score = Integer.parseInt(names[0].split("(\\) )")[1]);
        scoreTable.forEach(System.out::println);

        Collections.sort(scoreTable);
        StringBuilder expandedScoreTable = new StringBuilder();
        for (String s: scoreTable){
                expandedScoreTable.append(s).append("\n");
        }
        Platform.runLater(()-> {
            button_backToMenu.setVisible(true);
            button_backToMenu.setDisable(false);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Final score");

            alert.setHeaderText("Congratulations, " + thisPlayername + "! You " + position + " with the score of " + score + " points!\n\nScore table:\n" + expandedScoreTable);
            alert.setContentText("You can compare your score to the score of fellow players in the table on the right!");
            alert.setGraphic(new ImageView(this.getClass().getResource("graphics/medal.png").toString()));

            alert.showAndWait();
        });
    }

    public void disconnectedFromServer(){
        Platform.runLater(()-> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Connection error");
            alert.setHeaderText("Ooops, it seems like we are having problem with our server, " + thisPlayername + "!\n\n We apologize for any inconvenience caused by this interruption.");
            alert.setContentText("After clicking on \"OK\", your game will be closed.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent()){
                Platform.exit();
                System.exit(1);
            } else{
                Platform.exit();
                System.exit(1);
            }
        });
    }


    public void buildPlayerLabels(String[] names){
        System.out.println("build player names start");
        players = new ArrayList<>();
        System.out.println("players array number of elements: " + players.size());
        if(names.length > 0){
            Platform.runLater(()-> {
                String name;
                System.out.println("build player names 0: " + names[0]);

                if(names[0].startsWith("$&$START$&$")){
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
                String name;
                System.out.println("build player names 1: " + names[1]);

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
                System.out.println("players array number of elements after loading elements to it: " + players.size());

            });
        }
        if(names.length > 2){
            Platform.runLater(()-> {
                String name;
                System.out.println("build player names 2: " + names[2]);

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
                System.out.println("players array number of elements after loading elements to it: " + players.size());

            });
        }
        if(names.length > 3){
            Platform.runLater(()-> {
                String name;
                System.out.println("build player names 3: " + names[3]);

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
                System.out.println("players array number of elements after loading elements to it: " + players.size());

            });
        }
        if(names.length > 4){
            Platform.runLater(()-> {
                String name;
                System.out.println("build player names 4: " + names[4]);

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
                System.out.println("players array number of elements after loading elements to it: " + players.size());

            });
        }
        if(names.length > 5){
            Platform.runLater(()-> {
                String name;
                System.out.println("build player names 5: " + names[5]);

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
                System.out.println("players array number of elements after loading elements to it: " + players.size());

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
        //System.out.println("StackPane enabling: Name: " + stackPaneName + " and number of children: " + thePane.getChildren().size() + " and we should disable index 2");
        if(thePane.getChildren().size() != 0 && thePane.getChildren().size() != 1){
            thePane.getChildren().get(2).setDisable(!enable);
        }
    }

    public void enableSecondActionButtons(boolean enable){
        hand_StackPanes.forEach(pane -> {
            int numberOfChildren = pane.getChildren().size();
            //System.out.println("This pane is being enabled: " + pane.getId());
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
            System.out.println("Active index: " + activeIndex + " , max index: " + maxIndex + " active++: " + (activeIndex +1)  + " NUMBER OF PLAYERS ELEMENTS: " + numberOfPlayers);
            int newActiveIndex = 0;
            if (activeIndex + 1 <= maxIndex) {
                newActiveIndex = activeIndex + 1;
            }
            SimplePlayer newActivePlayer = players.get(newActiveIndex);
            activePlayer.setPlaying(false);
            newActivePlayer.setPlaying(true);
            changeActiveLabel(newActiveIndex);
            round++;
            System.out.println("ROUND: " + round + " , Next Active player is: " + newActivePlayer.getName() + "Index of next player: " + newActiveIndex);
            // newActivePlayer is this client (in players array its index 0

            if(activeIndex == 0){
                Platform.runLater(() -> {
                    enableFirstActionButtons(false);
                    enableSecondActionButtons(false);
                    System.out.println("After disabling my buttons, cuz I was active player");
                });
            }
            if (newActiveIndex == 0) {
                Platform.runLater(() -> {
                    enableFirstActionButtons(true);
                    System.out.println("After enabling buttons, cuz Im new active player");
                });
            }
        }
    }

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

    public void putEndGameTextOnLabel(){
        Platform.runLater(()-> {
            label_score.setText("10 cards are on the table! The score of all players will now be counted. When the counting is finished, you will see the score on the panel on the right!");
            enableSecondActionButtons(false);
            enableFirstActionButtons(false);
        });
    }
}
