package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {
    Stage window;
    private Parent rules;

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        rules = FXMLLoader.load(getClass().getResource("rules.fxml"));
        primaryStage.setTitle("Fantasy Realms TM");
        window.getIcons().add(new Image(Main.class.getResourceAsStream("graphics/icon.jpg")));
        // java - get screen size using the Toolkit class
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void show_rulesMain(ActionEvent event) throws IOException {
        Pane loader = FXMLLoader.load(getClass().getResource("rules.fxml"));

        window.getScene().setRoot(loader);
    }
}
