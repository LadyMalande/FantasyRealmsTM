package client;

import javafx.fxml.FXMLLoader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;


/**
 * Utility class for controlling navigation between vistas.
 *
 * All methods on the navigator are static to facilitate
 * simple access from anywhere in the application.
 *
 * Taken with the consent of the author from the web page on github: https://gist.github.com/jewelsea/6460130
 * @author jewelsea
 * @see <a href="https://gist.github.com/jewelsea/6460130">https://gist.github.com/jewelsea/6460130</a>
 */
public class SceneNavigator {

    // Convenience constants for fxml layouts managed by the navigator.

    /**
     * Holds the address of the FXML main window file.
     */
    static final String MAIN    = "main.fxml";
    /**
     * Holds the address of the FXML menu file.
     */
    static final String MENU = "menu.fxml";
    /**
     * Holds the address of the FXML rules file.
     */
    static final String RULES = "rules.fxml";
    /**
     * Holds the address of the FXML board file.
     */
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

            ResourceBundle resource = ResourceBundle.getBundle("client.UITexts", getLocaleFromConfig());
            mainController.setVista(
                    FXMLLoader.load(SceneNavigator.class.getResource(fxml), resource));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the locale for displaying the UI texts in the appropriate language.
     * @return Locale for the desired language.
     */
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
