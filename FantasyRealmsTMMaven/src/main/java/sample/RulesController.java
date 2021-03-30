package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Controller class for the first vista.
 */
public class RulesController {

    /**
     * Event handler fired when the user requests a new vista.
     *
     * @param event the event that triggered the handler.
     */
    @FXML
    void toMainMenu(ActionEvent event) {

        SceneNavigator.loadVista(SceneNavigator.MENU);
    }

}
