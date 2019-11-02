package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MyController_Menu extends Main implements Initializable {

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
        SceneNavigator.loadVista(SceneNavigator.RULES);
    }

    @FXML
    void start_game(ActionEvent event) throws IOException{
        SceneNavigator.loadVista(SceneNavigator.BOARD);
    }

}