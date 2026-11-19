package justintremblay.assignment02;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Car race simulation made with the Model View Controller pattern.
 * the view is the CarRace.fxml file, the controller is CarRaceController 
 * and the model is CarRaceModel with the Car class.
 * 
 * git: https://github.com/justinTremblayyy/assignment02.git
 * 
 * @author Justin Tremblay
 */
public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CarRace.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setTitle("Car Race Simulation");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
