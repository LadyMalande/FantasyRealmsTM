package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;



public class Main extends Application {
    Stage window;
    Parent menu_scene, rules_scene, game_scene;
    Button button_rules2;
    Button button_toMenu;

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        //Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));

        menu_scene = FXMLLoader.load(getClass().getResource("menu.fxml"));
        button_toMenu = new Button();
        button_toMenu.setOnAction(e -> window.setScene(new Scene(menu_scene, 800, 600)));
        rules_scene = FXMLLoader.load(getClass().getResource("rules.fxml"));
        button_rules2 = new Button();
        button_rules2.setOnAction(e -> window.setScene(new Scene(rules_scene, 800, 600)));
        primaryStage.setTitle("Fantasy Realms TM");
        window.getIcons().add(new Image(Main.class.getResourceAsStream("graphics/icon.jpg")));
        // java - get screen size using the Toolkit class
        primaryStage.setScene(new Scene(menu_scene, 800, 600));
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
