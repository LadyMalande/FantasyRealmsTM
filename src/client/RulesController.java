package client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the first vista.
 */
public class RulesController implements Initializable {

    public Pane rootPane;
    public Button button_toMenu;
    public Accordion rules_accordion;

    /**
     * Event handler fired when the user requests a new vista.
     *
     */
    @FXML
    void toMainMenu() {

        SceneNavigator.loadVista(SceneNavigator.MENU);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //lblTextByController.setText(resourceBundle.getString("key1"));
    }
}
