package client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Main controller class for the entering point of the entire layout.
 * Made by the design on the page https://gist.github.com/jewelsea/6460130.
 * Edited to serve the purpose for our application.
 * @author jewelsea
 * @see <a href="https://gist.github.com/jewelsea/6460130">https://gist.github.com/jewelsea/6460130</a>
 */
public class MainForApplication extends Application {

    /**
     * Starts the main window for the application.
     * @param stage Stage to show.
     * @throws Exception Thrown when some classes are missing or other problem occurs.
     */
    @Override
    public void start(Stage stage) throws Exception{
        stage.setTitle("Fantasy Realms TM");
        stage.setScene(
                createScene(
                        loadMainPane()
                )
        );

        stage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });

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

        Pane mainPane = loader.load(
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

    /**
     * Starts the graphic part of the application.
     * @param args Arguments passed to the UI.
     */
    public static void main(String[] args) {
        launch(args);
    }
}