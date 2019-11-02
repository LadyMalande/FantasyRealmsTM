package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller class for the second vista.
 */
public class BoardController {

    public List<Button> hand_Buttons;

    @FXML
    private Group group_hand_buttons;

    @FXML
    private Canvas canvas_hand1;

    @FXML
    private StackPane stack_hand1;

    @FXML
    private Button hand1, hand2, hand3, hand4, hand5, hand6, hand7, hand8;
/*
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        hand_Buttons.addAll(Arrays.asList(hand1, hand2, hand3, hand4, hand5, hand6, hand7, hand8));
        create_card();

    }
*/
    /**
     * Event handler fired when the user requests a previous vista.
     *
     * @param event the event that triggered the handler.
     */
    @FXML
    void previousPane(ActionEvent event) {
        SceneNavigator.loadVista(SceneNavigator.MENU);
    }

    void create_card(){
        Bounds bounds = hand1.localToScene(hand1.getBoundsInLocal());
        canvas_hand1 = new Canvas(500,500);

        GraphicsContext gc = canvas_hand1.getGraphicsContext2D();
        Bounds boundsInScene = hand1.localToScene(hand1.getBoundsInLocal());
        gc.setFill(Color.BLUE);
        gc.fillRect(bounds.getMinX() + 10,bounds.getMinY(),500,1000);
        group_hand_buttons.getChildren().add(canvas_hand1);
    }

}
