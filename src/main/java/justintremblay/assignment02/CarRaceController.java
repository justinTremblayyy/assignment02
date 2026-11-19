package justintremblay.assignment02;

import java.util.ArrayList;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
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
    private ArrayList<Label> resultLabels;
    private ArrayList<TranslateTransition> moves;
    private ArrayList<RotateTransition> spins;
    private ScaleTransition winnerPop;
    private boolean started;
    
    private boolean paused;

    public void initialize() {
        model = new CarRaceModel();
        carShapes = new ArrayList<>();
        wheels = new ArrayList<>();
        resultLabels = new ArrayList<>();
        moves = new ArrayList<>();
        spins = new ArrayList<>();

        drawLanes();
        drawFinishLine();
        for (Car car : model.getCars()) {
            Group carShape = createCar(car);
            carShapes.add(carShape);
            trackPane.getChildren().add(carShape);
        }

        resultLabels.add(resultLabel1);
        resultLabels.add(resultLabel2);
        resultLabels.add(resultLabel3);
        resultLabels.add(resultLabel4);

        speedSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            changeSpeed(newValue.doubleValue());
        });

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
        
        if (paused) {
            for (int i = 0; i < moves.size(); i++) {
                if (carShapes.get(i).getTranslateX() < CarRaceModel.DISTANCE) {
                    moves.get(i).play();
                    spins.get(i * 2).play();
                    spins.get(i * 2 + 1).play();
                }
            }
            paused = false;
            titleLabel.setText("Race in Progress");
            messageLabel.setText("The race continues!");
            return;
        }
        
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
            
            move.setRate(speedSlider.getValue());
            
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
        
        if (started && !paused && !model.isRaceOver()) {
            
            for (TranslateTransition move : moves) {
                move.pause();
            }
            
            for (RotateTransition spin : spins) {
                spin.pause();
            }
            paused = true;
            titleLabel.setText("Race Paused");
            messageLabel.setText("Press Start to continue the race.");
        }
    }

    @FXML
    private void resetButtonPressed(ActionEvent event) {
        
        for (TranslateTransition move : moves) {
            move.stop();
        }
        
        for (RotateTransition spin : spins) {
            spin.stop();
        }
        
        moves.clear();
        spins.clear();
        started = false;
        paused = false;

        if (winnerPop != null) {
            winnerPop.stop();
        }
        for (Group carShape : carShapes) {
            carShape.setTranslateX(0);
            carShape.setScaleX(1);
            carShape.setScaleY(1);
        }
        
        for (Group wheel : wheels) {
            wheel.setRotate(0);
        }
        
        model.reset();
        titleLabel.setText("Ready to Race");
        messageLabel.setText("Press Start to begin the race.");
        infoLabel.setText("");
        for (Label resultLabel : resultLabels) {
            resultLabel.setText("-");
        }
    }
    

    private RotateTransition createSpin(Group wheel, Car car) {
        RotateTransition spin = new RotateTransition(Duration.seconds(60 / car.getSpeed()), wheel);
        spin.setByAngle(360);
        spin.setCycleCount(Animation.INDEFINITE);
        spin.setInterpolator(Interpolator.LINEAR);
        
        spin.setRate(speedSlider.getValue());
        
        return spin;
    }

    private void carMoved(int index, double distance) {
        Car car = model.getCars().get(index);
        model.updatePosition(car, distance);

        if (started && model.hasCrossed(car) && !model.hasFinished(car)) {
            model.carFinished(car);
            int place = model.getFinishOrder().size();
            if (place == 1) {
                messageLabel.setText(car + " crossed the finish line first!");
            } else {
                messageLabel.setText(car + " finished " + getPlaceName(place) + ".");
            }
            if (model.isRaceOver()) {
                showResults();
            }
        }

        if (distance >= CarRaceModel.DISTANCE && spins.size() > index * 2 + 1) {
            spins.get(index * 2).stop();
            spins.get(index * 2 + 1).stop();
        }
    }

    
    private void changeSpeed(double speed) {
        
        for (TranslateTransition move : moves) {
            move.setRate(speed);
        }
        
        for (RotateTransition spin : spins) {
            spin.setRate(speed);
        }
        speedLabel.setText(String.format("%.1fx", speed));
    }

    private String getPlaceName(int place) {
        if (place == 1) {
            return "1st";
        } else if (place == 2) {
            return "2nd";
        } else if (place == 3) {
            return "3rd";
        }
        return place + "th";
    }

    private void showResults() {
        Car winner = model.getWinner();
        titleLabel.setText("Race Finished!");
        messageLabel.setText("Congratulations! " + winner + " is the winner!");
        for (int i = 0; i < model.getFinishOrder().size(); i++) {
            Car car = model.getFinishOrder().get(i);
            resultLabels.get(i).setText(getPlaceName(i + 1) + "    " + car + "  " + car.getType());
        }
        infoLabel.setText("Great race! Translate, Rotate, Scale and Fade transitions were used.");

        FadeTransition fade = new FadeTransition(Duration.millis(800), messageBox);
        fade.setFromValue(0.2);
        fade.setToValue(1.0);
        fade.play();

        Group winnerShape = carShapes.get(model.getCars().indexOf(winner));
        winnerPop = new ScaleTransition(Duration.millis(400), winnerShape);
        winnerPop.setToX(1.2);
        winnerPop.setToY(1.2);
        winnerPop.setCycleCount(4);
        winnerPop.setAutoReverse(true);
        winnerPop.play();
    }
    
}
