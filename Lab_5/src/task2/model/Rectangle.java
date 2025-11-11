package task2.model;

public class Rectangle extends Shape {
    private static final long serialVersionUID = 1L;
    private double width;
    private double height;

    public Rectangle(String shapeColor, double width, double height) {
        super(shapeColor);
        this.width = width;
        this.height = height;
    }

    @Override
    public double calcArea() { return width * height; }

    @Override
    public void draw() { System.out.println("Малюємо прямокутник"); }

    @Override
    public String toString() {
        return String.format("Rectangle [ширина: %.1f, висота: %.1f] (колір: %s, площа: %.2f)",
                width, height, shapeColor, calcArea());
    }
}