package task2.model;

import java.io.Serializable;

public abstract class Shape implements Drawable, Serializable {

    private static final long serialVersionUID = 1L;

    protected String shapeColor;

    public Shape(String shapeColor) {
        this.shapeColor = shapeColor;
    }

    public abstract double calcArea();

    public String getShapeColor() {
        return shapeColor;
    }

    @Override
    public String toString() {
        return String.format("%s (колір: %s, площа: %.2f)",
                getClass().getSimpleName(), shapeColor, calcArea());
    }
}