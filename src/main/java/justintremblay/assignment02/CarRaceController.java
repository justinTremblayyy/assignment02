package justintremblay.assignment02;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

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
    }
}
