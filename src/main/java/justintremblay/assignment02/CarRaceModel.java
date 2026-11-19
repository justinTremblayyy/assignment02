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
    private ArrayList<Car> finishOrder;

    public CarRaceModel() {
        cars = new ArrayList<>();
        finishOrder = new ArrayList<>();
        cars.add(new Car("Sedan", 23, START_X, 8, 150, Color.ROYALBLUE, "Blue"));
        cars.add(new Car("Sports Car", 45, START_X, LANE_HEIGHT + 8, 150, Color.RED, "Red"));
        cars.add(new Car("Coupe", 78, START_X, LANE_HEIGHT * 2 + 8, 150, Color.LIMEGREEN, "Green"));
        cars.add(new Car("Taxi", 88, START_X, LANE_HEIGHT * 3 + 8, 150, Color.GOLD, "Yellow"));
    }

    public ArrayList<Car> getCars() {
        return cars;
    }

    public ArrayList<Car> getFinishOrder() {
        return finishOrder;
    }

    public void randomizeSpeeds() {
        for (Car car : cars) {
            car.setSpeed(120 + Math.random() * 60);
        }
    }

    public void updatePosition(Car car, double distance) {
        car.setX(START_X + distance);
    }

    public boolean hasCrossed(Car car) {
        return car.getX() + CAR_LENGTH >= FINISH_X;
    }

    public boolean hasFinished(Car car) {
        return finishOrder.contains(car);
    }

    public void carFinished(Car car) {
        if (!finishOrder.contains(car)) {
            finishOrder.add(car);
        }
    }

    public boolean isRaceOver() {
        return finishOrder.size() == cars.size();
    }

    public Car getWinner() {
        if (finishOrder.isEmpty()) {
            return null;
        }
        return finishOrder.get(0);
    }

    public void reset() {
        finishOrder.clear();
        for (Car car : cars) {
            car.setX(START_X);
        }
    }
}
