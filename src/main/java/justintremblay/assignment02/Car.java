package justintremblay.assignment02;

import javafx.scene.paint.Color;

public class Car {
    private String type;
    private int number;
    private double x;
    private double y;
    private double speed;
    private Color color;
    private String colorName;

    public Car(String type, int number, double x, double y, double speed, Color color, String colorName) {
        this.type = type;
        this.number = number;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.color = color;
        this.colorName = colorName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    @Override
    public String toString() {
        return "Car #" + number + " (" + colorName + ")";
    }
}
