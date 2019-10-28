package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MyController_Menu extends Main implements Initializable {

    @FXML
    public VBox rootVBox;

    @FXML
    private Button button_rules;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void exit(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void show_rules(ActionEvent event) throws IOException {
        //show_rulesMain(event);
        Pane loader = FXMLLoader.load(getClass().getResource("rules.fxml"));
       // window.getScene().setRoot(loader);
        rootVBox.getChildren().setAll(loader);
    }

    @FXML
    void start_game(ActionEvent event) {

    }

}