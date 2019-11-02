package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        stage.setTitle("Fantasy Realms TM");
        stage.setScene(
                createScene(
                        loadMainPane()
                )
        );

        stage.show();
    }

    /**
     * Loads the main fxml layout.
     * Sets up the vista switching VistaNavigator.
     * Loads the first vista into the fxml layout.
     *
     * @return the loaded pane.
     * @throws IOException if the pane could not be loaded.
     */
    private Pane loadMainPane() throws IOException {
        FXMLLoader loader = new FXMLLoader();

        Pane mainPane = (Pane) loader.load(
                getClass().getResourceAsStream(
                        SceneNavigator.MAIN
                )
        );

        MainController mainController = loader.getController();

        SceneNavigator.setMainController(mainController);
        SceneNavigator.loadVista(SceneNavigator.MENU);

        return mainPane;
    }

    /**
     * Creates the main application scene.
     *
     * @param mainPane the main application layout.
     *
     * @return the created scene.
     */
    private Scene createScene(Pane mainPane) {
        Scene scene = new Scene(
                mainPane
        );

        scene.getStylesheets().setAll(
                getClass().getResource("style.css").toExternalForm()
        );

        return scene;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
/*
public class Main extends Application {


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
        window.setTitle("Fantasy Realms TM");
        window.getIcons().add(new Image(Main.class.getResourceAsStream("graphics/icon.jpg")));
        // java - get screen size using the Toolkit class
        window.setScene(new Scene(menu_scene, 800, 600));
        window.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
*/