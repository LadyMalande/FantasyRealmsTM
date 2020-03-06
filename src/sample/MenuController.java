package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class MenuController extends Main implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        BoardController.randomDeck = false;
        SceneNavigator.loadVista(SceneNavigator.BOARD);
    }

    @FXML
    void start_gameRandom() {
        BoardController.randomDeck = true;
        SceneNavigator.loadVista(SceneNavigator.BOARD);
    }



}