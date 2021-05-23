package client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * Menu controller for the menu page of the application.
 * Made to fit the design on the page https://gist.github.com/jewelsea/6460130.
 * @author Tereza Miklóšová
 */
public class MenuController extends MainForApplication implements Initializable {

    /**
     * Button to set the Czech locale for displaying all texts in Czech.
     */
    @FXML
    public Button button_locale_cs;

    /**
     * Button to set the English locale for displaying all texts in Czech.
     */
    @FXML
    public Button button_locale_en;

    /**
     * If the UI got the name of the player that means all information is filled in to start a new game.
     * If not the player clicked cancel at some point of the process and there is not enough information
     * to start the game.
     */
    boolean gotName;

    /**
     * Overrides the method for initializing the UI. All is drawn by the fxml, there is no other loading needed.
     * @param url From the Initializable interface.
     * @param resourceBundle From the Initializable interface.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Menu was drawn");
    }

    /**
     * Exits the UI and the whole application.
     */
    @FXML
    void exit() {
        Platform.exit();
        System.exit(0);
    }

    /**
     * Loads the page with rules.
     */
    @FXML
    void show_rules() {
        SceneNavigator.loadVista(SceneNavigator.RULES);
    }

    /**
     * Queries the player for more information to start the game. If all is filled in, the Board is loaded and
     * server is contacted to start the game.
     * Starts the original deck game.
     */
    @FXML
    void start_game() {
        if(showDialogs()){
            setValueInPropertiesFile("randomdeck","false");
            SceneNavigator.loadVista(SceneNavigator.BOARD);
        }
    }

    /**
     * Queries the player for more information to start the game. If all is filled in, the Board is loaded and
     * server is contacted to start the game.
     * Starts the random deck game.
     */
    @FXML
    void start_gameRandom() {
        if(showDialogs()){
            setValueInPropertiesFile("randomdeck","true");
            SceneNavigator.loadVista(SceneNavigator.BOARD);
        }
    }

    /**
     * Sets the UI locale to English.
     */
    @FXML
    void change_localization_en() {
        setValueInPropertiesFile("locale", "en");
        SceneNavigator.loadVista(SceneNavigator.MENU);
    }

    /**
     * Sets the UI locale to Czech.
     */
    @FXML
    void change_localization_cs() {
        setValueInPropertiesFile("locale", "cs");
        SceneNavigator.loadVista(SceneNavigator.MENU);
    }

    /**
     * Fills information about the player to config file.
     * @param key Which information is going to be set.
     * @param value What value has the information that is being set.
     */
    private void setValueInPropertiesFile(String key, String value){
        Properties props = new Properties();
        File configFile = new File("config.properties");
        try{
            FileReader reader = new FileReader(configFile);
            // load the properties file:
            props.load(reader);

            props.setProperty(key, value);

            FileWriter writer = new FileWriter(configFile);
            props.store(writer, "client settings");

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets a value by the key from the config file.
     * @param key Key that we want to get from the config file.
     * @return Value of the key queried.
     */
    public static String getValueInPropertiesFile(String key){
        Properties props = new Properties();
        File configFile = new File("config.properties");
        String value = "";
        try{
            FileReader reader = new FileReader(configFile);
            // load the properties file:
            props.load(reader);

            value = props.getProperty(key);

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * Shows several dialogs to get information about the player and the game he wishes to play.
     * @return True if all dialogs are completed successfully. False otherwise.
     */
    private boolean showDialogs(){
        if(show_dialog_HowManyPlayers()){
            show_dialog_howManyAI();
            show_dialog_InsertYourName();
        }
        return gotName;
    }

    /**
     * Shows dialog asking the player how many players overall he wants to have in the game.
     * @return True if the dialog is filled and OK is selected. False is the dialog is cancelled.
     */
    private boolean show_dialog_HowManyPlayers(){
        Locale locale = new Locale(getValueInPropertiesFile("locale"));
        ResourceBundle rb = ResourceBundle.getBundle("client.UITexts", locale);
        ArrayList<String> howManyPlayers = new ArrayList<>(){{add("2");add("3");add("4");add("5");add("6");}};

        ChoiceDialog<String> dialog = new ChoiceDialog<>(howManyPlayers.get(0), howManyPlayers);
        dialog.setTitle(rb.getString("menu_serversetup"));
        dialog.setHeaderText(rb.getString("menu_numberofplayers"));
        dialog.setContentText(rb.getString("menu_choosenumberofplayers"));

        Optional<String> result = dialog.showAndWait();
        String players = "0";
        try{
            players = result.orElse("0");
        } catch(NoSuchElementException ignored){

        }
        if (!players.equals("0")){
            setValueInPropertiesFile("menu_numberofplayers", players);
            return true;
        } else{
            System.out.println("Cancel button was pressed2");
            return false;
        }
    }

    /**
     * Shows a dialog window to ask the player about his name. If the name is filled in and OK is pressed, variable to
     * enable continuation is set.
     */
    private void show_dialog_InsertYourName(){
        Locale locale = new Locale(getValueInPropertiesFile("locale"));
        ResourceBundle rb = ResourceBundle.getBundle("client.UITexts", locale);
        Random r = new Random(5032);
        TextInputDialog dialog = new TextInputDialog("your_name" + r.nextInt(1000));
        dialog.setTitle(rb.getString("menu_playersetup"));
        dialog.setHeaderText(rb.getString("menu_yourname"));
        dialog.setContentText(rb.getString("menu_entername"));

        // Traditional way to get the response value.
        final Button cancel = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancel.setOnAction(event -> {
            gotName = false;
            System.out.println("Cancel was definitely pressed");

        });
        final Button ok = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        ok.setOnAction(event -> {
            gotName = true;
            System.out.println("OK was definitely pressed");

        });

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(s -> setValueInPropertiesFile("name", s));
    }

    /**
     * Shows a dialog to ask the player how many AIs hw wishes to have in game.
     * If the player did not choose single player mode for testing, he may choose how many AIs he wants to play with.
     */
    private void show_dialog_howManyAI(){
        setValueInPropertiesFile("numberofai", "0");
        Locale locale = new Locale(getValueInPropertiesFile("locale"));
        ResourceBundle rb = ResourceBundle.getBundle("client.UITexts", locale);
        int totalPlayers = Integer.parseInt(getValueInPropertiesFile("menu_numberofplayers"));
        if(totalPlayers != 1) {
            ArrayList<String> howManyAIs = new ArrayList<>();
            for(int i = 0; i < totalPlayers; i++){
                howManyAIs.add(Integer.toString(i));
            }
            ChoiceDialog<String> dialog = new ChoiceDialog<>(howManyAIs.get(0), howManyAIs);
            dialog.setTitle(rb.getString("menu_serversetup"));
            dialog.setHeaderText(rb.getString("menu_numberofai"));
            dialog.setContentText(rb.getString("menu_numberofaitext"));

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(s -> setValueInPropertiesFile("numberofai", s));
        }
    }
}