package justintremblay.assignment02;

import java.util.ArrayList;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Controller of the car race. Draws the cars on the track and handles the buttons, the
 * slider, the slideshow and all the animations of the race.
 *
 * @author Justin Tremblay
 */
public class CarRaceController {
    @FXML
    private Pane trackPane;

    @FXML
    private Slider speedSlider;

    @FXML
    private Label speedLabel;

    @FXML
    private VBox messageBox;

    @FXML
    private Label titleLabel;

    @FXML
    private Label messageLabel;

    @FXML
    private Label infoLabel;

    @FXML
    private Label resultLabel1;

    @FXML
    private Label resultLabel2;

    @FXML
    private Label resultLabel3;

    @FXML
    private Label resultLabel4;

    private CarRaceModel model;
    private ArrayList<Group> carShapes;
    private ArrayList<Group> wheels;
    private ArrayList<TranslateTransition> moves;
    private ArrayList<RotateTransition> spins;
    private boolean started;

    public void initialize() {
        model = new CarRaceModel();
        carShapes = new ArrayList<>();
        wheels = new ArrayList<>();
        moves = new ArrayList<>();
        spins = new ArrayList<>();

        drawLanes();
        drawFinishLine();
        for (Car car : model.getCars()) {
            Group carShape = createCar(car);
            carShapes.add(carShape);
            trackPane.getChildren().add(carShape);
        }

        for (int i = 0; i < carShapes.size(); i++) {
            int index = i;
            carShapes.get(i).translateXProperty().addListener((observable, oldValue, newValue) -> {
                carMoved(index, newValue.doubleValue());
            });
        }
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

    private Group createCar(Car car) {
        Polygon body = new Polygon(0.0, 32.0, 10.0, 22.0, 30.0, 20.0, 82.0, 20.0,
                102.0, 25.0, 110.0, 32.0, 110.0, 42.0, 0.0, 42.0);
        body.setFill(car.getColor());
        body.setStroke(Color.BLACK);

        Polygon roof = new Polygon(28.0, 20.0, 40.0, 5.0, 74.0, 5.0, 86.0, 20.0);
        roof.setFill(car.getColor());
        roof.setStroke(Color.BLACK);

        Rectangle backWindow = new Rectangle(39, 8, 17, 11);
        backWindow.setFill(Color.LIGHTSKYBLUE);
        Rectangle frontWindow = new Rectangle(59, 8, 17, 11);
        frontWindow.setFill(Color.LIGHTSKYBLUE);

        Rectangle plate = new Rectangle(42, 24, 28, 15);
        plate.setFill(Color.WHITE);
        plate.setArcWidth(6);
        plate.setArcHeight(6);
        Text numberText = new Text(48, 36, String.valueOf(car.getNumber()));
        numberText.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        Rectangle headlight = new Rectangle(103, 27, 6, 5);
        headlight.setFill(Color.LIGHTYELLOW);
        headlight.setStroke(Color.BLACK);

        Group backWheel = createWheel(25, 42);
        Group frontWheel = createWheel(85, 42);
        wheels.add(backWheel);
        wheels.add(frontWheel);

        Group carShape = new Group(body, roof, backWindow, frontWindow, plate, numberText,
                headlight, backWheel, frontWheel);
        carShape.setLayoutX(car.getX());
        carShape.setLayoutY(car.getY());
        return carShape;
    }

    private Group createWheel(double x, double y) {
        Circle tire = new Circle(x, y, 9, Color.BLACK);
        tire.setStroke(Color.GRAY);
        tire.setStrokeWidth(2);
        Line spoke = new Line(x, y, x, y - 8);
        spoke.setStroke(Color.WHITE);
        spoke.setStrokeWidth(2);
        return new Group(tire, spoke);
    }

    @FXML
    private void startButtonPressed(ActionEvent event) {
        if (started) {
            return;
        }
        started = true;
        model.randomizeSpeeds();
        moves.clear();
        spins.clear();

        for (int i = 0; i < model.getCars().size(); i++) {
            Car car = model.getCars().get(i);
            TranslateTransition move = new TranslateTransition(
                    Duration.seconds(CarRaceModel.DISTANCE / car.getSpeed()), carShapes.get(i));
            move.setFromX(0);
            move.setToX(CarRaceModel.DISTANCE);
            move.setInterpolator(Interpolator.LINEAR);
            moves.add(move);
            spins.add(createSpin(wheels.get(i * 2), car));
            spins.add(createSpin(wheels.get(i * 2 + 1), car));
        }

        for (TranslateTransition move : moves) {
            move.play();
        }
        for (RotateTransition spin : spins) {
            spin.play();
        }
        titleLabel.setText("Race in Progress");
        messageLabel.setText("The race has started!");
        infoLabel.setText("TranslateTransition moves the cars, RotateTransition spins the wheels.");
    }

    @FXML
    private void pauseButtonPressed(ActionEvent event) {
    }

    @FXML
    private void resetButtonPressed(ActionEvent event) {
    }

    private RotateTransition createSpin(Group wheel, Car car) {
        RotateTransition spin = new RotateTransition(Duration.seconds(60 / car.getSpeed()), wheel);
        spin.setByAngle(360);
        spin.setCycleCount(Animation.INDEFINITE);
        spin.setInterpolator(Interpolator.LINEAR);
        return spin;
    }

    private void carMoved(int index, double distance) {
        Car car = model.getCars().get(index);
        model.updatePosition(car, distance);

        if (distance >= CarRaceModel.DISTANCE && spins.size() > index * 2 + 1) {
            spins.get(index * 2).stop();
            spins.get(index * 2 + 1).stop();
        }
    }
}
