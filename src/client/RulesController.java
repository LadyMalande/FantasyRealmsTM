package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controller class for the Rules page of the application.
 * Made to fit the overall design on the page https://gist.github.com/jewelsea/6460130.
 * @author Tereza Miklóšová
 * @see <a href="https://gist.github.com/jewelsea/6460130">https://gist.github.com/jewelsea/6460130</a>
 */
public class RulesController implements Initializable {

    /**
     * The root pane for the page.
     */
    public Pane rootPane;

    /**
     * Button to get back to menu.
     */
    public Button button_toMenu;

    /**
     * Accordion containing the rules.
     */
    public Accordion rules_accordion;
    public WebView webView;
    public Button button_showCredits;

    /**
     * Event handler fired when the user requests a new vista.
     */
    @FXML
    void toMainMenu() {
        SceneNavigator.loadVista(SceneNavigator.MENU);
    }

    /**
     * The override for initialize method which is mandatory for classes implementing Initializable.
     * Everything is loaded and static in the rules.fxml.
     * @param url From the Initializable interface.
     * @param resourceBundle From the Initializable interface.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    public void showCredits(ActionEvent event) {
        ResourceBundle rb = ResourceBundle.getBundle("client.UITexts", new Locale(MenuController.getValueInPropertiesFile("locale")));
        button_showCredits.setText(rb.getString("hideCredits"));
        button_showCredits.setOnAction(this::hideCredits);
        webView.setVisible(true);
        WebEngine webEngine = webView.getEngine();
        URL url = this.getClass().getResource("/client/resources/credits.html");
        webEngine.load(url.toString());
    }

    @FXML
    public void hideCredits(ActionEvent event) {
        ResourceBundle rb = ResourceBundle.getBundle("client.UITexts", new Locale(MenuController.getValueInPropertiesFile("locale")));
        button_showCredits.setText(rb.getString("showCredits"));
        button_showCredits.setOnAction(this::showCredits);
        webView.setVisible(false);
    }
}
