package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the first vista.
 */
public class RulesController implements Initializable {

    /**
     * Event handler fired when the user requests a new vista.
     *
     * @param event the event that triggered the handler.
     */
    @FXML
    void toMainMenu(ActionEvent event) {

        SceneNavigator.loadVista(SceneNavigator.MENU);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //lblTextByController.setText(resourceBundle.getString("key1"));
    }
}
