package client;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * Main controller class for the entire layout.
 * Made by the design on the page https://gist.github.com/jewelsea/6460130.
 * @author jewelsea
 * @see <a href="https://gist.github.com/jewelsea/6460130">https://gist.github.com/jewelsea/6460130</a>
 */
public class MainController {

    /** Holder of a switchable vista. */
    @FXML
    private StackPane vistaHolder;

    /**
     * Replaces the vista displayed in the vista holder with a new vista.
     *
     * @param node the vista node to be swapped in.
     */
    void setVista(Node node) {
        vistaHolder.getChildren().setAll(node);
    }

}
