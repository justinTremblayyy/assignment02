package justintremblay.assignment02;

import java.util.ArrayList;
import javafx.scene.paint.Color;

/**
 * Model of the car race. Stores the cars, their positions and the finishing order.
 *
 * @author Justin Tremblay
 */
public class CarRaceModel {
    public static final double START_X = 50;
    public static final double FINISH_X = 820;
    public static final double CAR_LENGTH = 110;
    public static final double LANE_HEIGHT = 65;
    public static final double DISTANCE = 690;

    private ArrayList<Car> cars;

    public CarRaceModel() {
        cars = new ArrayList<>();
        cars.add(new Car("Sedan", 23, START_X, 8, 150, Color.ROYALBLUE, "Blue"));
        cars.add(new Car("Sports Car", 45, START_X, LANE_HEIGHT + 8, 150, Color.RED, "Red"));
        cars.add(new Car("Coupe", 78, START_X, LANE_HEIGHT * 2 + 8, 150, Color.LIMEGREEN, "Green"));
        cars.add(new Car("Taxi", 88, START_X, LANE_HEIGHT * 3 + 8, 150, Color.GOLD, "Yellow"));
    }

    public ArrayList<Car> getCars() {
        return cars;
    }
}
