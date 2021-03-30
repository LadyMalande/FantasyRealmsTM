package sample;

import javafx.fxml.FXMLLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;


/**
 * Utility class for controlling navigation between vistas.
 *
 * All methods on the navigator are static to facilitate
 * simple access from anywhere in the application.
 */
class SceneNavigator {

    /**
     * Convenience constants for fxml layouts managed by the navigator.
     */
    static final String MAIN    = "main.fxml";
    static final String MENU = "menu.fxml";
    static final String RULES = "rules.fxml";
    static final String BOARD   = "board.fxml";

    /** The main application layout controller. */
    private static MainController mainController;

    /**
     * Stores the main controller for later use in navigation tasks.
     *
     * @param mainController the main application layout controller.
     */
    static void setMainController(MainController mainController) {
        SceneNavigator.mainController = mainController;
    }

    /**
     * Loads the vista specified by the fxml file into the
     * vistaHolder pane of the main application layout.
     *
     * Previously loaded vista for the same fxml file are not cached.
     * The fxml is loaded anew and a new vista node hierarchy generated
     * every time this method is invoked.
     *
     * A more sophisticated load function could potentially add some
     * enhancements or optimizations, for example:
     *   cache FXMLLoaders
     *   cache loaded vista nodes, so they can be recalled or reused
     *   allow a user to specify vista node reuse or new creation
     *   allow back and forward history like a browser
     *
     * @param fxml the fxml file to be loaded.
     */
    static void loadVista(String fxml) {
        try {

            ResourceBundle resource = ResourceBundle.getBundle("sample.UITexts", getLocaleFromConfig());
            mainController.setVista(
                    FXMLLoader.load(SceneNavigator.class.getResource(fxml), resource));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Locale getLocaleFromConfig(){
        Properties props = new Properties();
        File configFile = new File("config.properties");
        Locale locale = new Locale("en");
        try {
            FileReader reader = new FileReader(configFile);
            // load the properties file:
            props.load(reader);

            locale = new Locale(props.getProperty("locale"));

            reader.close();
        } catch(IOException ex){
            ex.printStackTrace();
        }
        return locale;
    }

}
