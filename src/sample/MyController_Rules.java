package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MyController_Rules extends Main implements Initializable
{
    @FXML
    private Pane rootPane;

    @FXML
    private Label gameplay_list;

    @FXML
    private Accordion rules_accordion;

    @FXML
    void toMain_Menu(ActionEvent event) throws IOException {
        Parent menu = FXMLLoader.load(getClass().getResource("menu.fxml"));
        window.setScene(new Scene(menu, 800, 600));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rootPane = new Pane();
        rules_accordion.setMaxSize(1000.0,600.0);
        System.out.println("Jsem v konstruktoru initialize");
    }

    private static final String GAMEPLAY_LIST =
            "1. Name - Every card has a unique name.\n" +
                    "2. Suit - Each suit has its own color. There are ten suits: Army, Leader, Wizard, Weapon, Artifact, Beast, Land, Weather, Flood and Flame, along with three Wild cards.\n" +
                    "3. Base strenght - Base strengths run from 0 to 40. &#13;";
}
