package sample;

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

public class MenuController extends MainForApplication implements Initializable {

    boolean gotName;
    @FXML
    private Button button_locale_en, button_locale_cs;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Menu was drawn");
    }

    @FXML
    void exit() {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void show_rules() {
        SceneNavigator.loadVista(SceneNavigator.RULES);
    }

    @FXML
    void start_game() {
        if(showDialogs()){
            setValueInPropertiesFile("randomdeck","false");
            SceneNavigator.loadVista(SceneNavigator.BOARD);
        }
    }

    @FXML
    void start_gameRandom() {
        if(showDialogs()){
            setValueInPropertiesFile("randomdeck","true");
            SceneNavigator.loadVista(SceneNavigator.BOARD);
        }
    }

    @FXML
    void change_localization_en() {
        setValueInPropertiesFile("locale", "en");
        SceneNavigator.loadVista(SceneNavigator.MENU);
    }

    @FXML
    void change_localization_cs() {
        setValueInPropertiesFile("locale", "cs");
        SceneNavigator.loadVista(SceneNavigator.MENU);
    }

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

    private String getValueInPropertiesFile(String key){
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

    private boolean showDialogs(){
        if(show_dialog_HowManyPlayers()){
            show_dialog_howManyAI();
            show_dialog_InsertYourName();


        }
        return gotName;
    }

    private boolean show_dialog_HowManyPlayers(){
        Locale locale = new Locale(getValueInPropertiesFile("locale"));
        ResourceBundle rb = ResourceBundle.getBundle("sample.UITexts", locale);
        ArrayList<String> howManyPlayers = new ArrayList<>(){{add("1");add("2");add("3");add("4");add("5");add("6");}};

        ChoiceDialog dialog = new ChoiceDialog();
        dialog.getItems().addAll(howManyPlayers);
        dialog.setTitle(rb.getString("menu_serversetup"));
        dialog.setHeaderText(rb.getString("menu_numberofplayers"));
        dialog.setContentText(rb.getString("menu_choosenumberofplayers"));

        Optional<String> result = dialog.showAndWait();
        String players = "0";
        try{
            players = result.get();
        } catch(NoSuchElementException el){

        }
        if (!players.equals("0")){
            setValueInPropertiesFile("menu_numberofplayers", players);
            return true;
        } else{
            System.out.println("Cancel button was pressed2");
            return false;
        }
    }

    private void show_dialog_InsertYourName(){
        Locale locale = new Locale(getValueInPropertiesFile("locale"));
        ResourceBundle rb = ResourceBundle.getBundle("sample.UITexts", locale);
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
        if(result.isPresent()){
            setValueInPropertiesFile("name", result.get());
        }


    }

    private void show_dialog_howManyAI(){
        setValueInPropertiesFile("numberofai", "0");
        Locale locale = new Locale(getValueInPropertiesFile("locale"));
        ResourceBundle rb = ResourceBundle.getBundle("sample.UITexts", locale);
        int totalPlayers = Integer.parseInt(getValueInPropertiesFile("menu_numberofplayers"));
        if(totalPlayers != 1) {
            ArrayList<String> howManyAIs = new ArrayList<>();
            for(int i = 0; i < totalPlayers; i++){
                howManyAIs.add(Integer.toString(i));
            }
            ChoiceDialog dialog = new ChoiceDialog();
            dialog.getItems().addAll(howManyAIs);
            dialog.setTitle(rb.getString("menu_serversetup"));
            dialog.setHeaderText(rb.getString("menu_numberofai"));
            dialog.setContentText(rb.getString("menu_numberofaitext"));

            Optional<String> result = dialog.showAndWait();
            if(result.isPresent()){
                setValueInPropertiesFile("numberofai", result.get());

            }
        }
    }


}