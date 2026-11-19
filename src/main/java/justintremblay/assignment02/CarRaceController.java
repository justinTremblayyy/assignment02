package justintremblay.assignment02;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Controller of the car race. Draws the cars on the track and handles the buttons, the
 * slider, the slideshow and all the animations of the race.
 *
 * @author Justin Tremblay
 */
public class CarRaceController {
    @FXML
    private Pane trackPane;

    private CarRaceModel model;

    public void initialize() {
        model = new CarRaceModel();

        drawLanes();
        drawFinishLine();
    }

    private void drawLanes() {
        for (int lane = 1; lane < 4; lane++) {
            double y = lane * CarRaceModel.LANE_HEIGHT;
            for (double x = 60; x < 840; x += 40) {
                Rectangle dash = new Rectangle(x, y - 2, 22, 4);
                dash.setFill(Color.WHITE);
                trackPane.getChildren().add(dash);
            }
        }
    }

    private void drawFinishLine() {
        for (int row = 0; row < 52; row++) {
            for (int column = 0; column < 2; column++) {
                Rectangle square = new Rectangle(CarRaceModel.FINISH_X - 5 + column * 5, row * 5, 5, 5);
                if ((row + column) % 2 == 0) {
                    square.setFill(Color.WHITE);
                } else {
                    square.setFill(Color.BLACK);
                }
                trackPane.getChildren().add(square);
            }
        }
    }
}
