package task2.model;

public class Triangle extends Shape {
    private static final long serialVersionUID = 1L;
    private double base;
    private double height;

    public Triangle(String shapeColor, double base, double height) {
        super(shapeColor);
        this.base = base;
        this.height = height;
    }

    @Override
    public double calcArea() { return (base * height) / 2; }

    @Override
    public void draw() { System.out.println("Малюємо трикутник"); }

    @Override
    public String toString() {
        return String.format("Triangle [основа: %.1f, висота: %.1f] (колір: %s, площа: %.2f)",
                base, height, shapeColor, calcArea());
    }
}