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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MyController_Menu extends Main implements Initializable {

    @FXML
    public Pane rootPane;

    @FXML
    public VBox rootVBox;

    @FXML
    public Button button_rules;

    @FXML
    public Button button_toMenu;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            button_rules = button_rules2;
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
/*
        Stage stage;
        Parent root;

        if(event.getSource()==button_rules){
            stage = (Stage) button_rules.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("rules.fxml"));
        }
        else{
            stage = (Stage) button_toMenu.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

 */
    }



    @FXML
    void toMain_Menu(ActionEvent event) throws IOException {
        //show_rulesMain(event);
        Pane loader = FXMLLoader.load(getClass().getResource("menu.fxml"));
        // window.getScene().setRoot(loader);
        rootPane.getChildren().setAll(loader);
    }

    @FXML
    void start_game(ActionEvent event) {

    }

}