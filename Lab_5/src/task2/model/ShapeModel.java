package task2.model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class ShapeModel {
    private Shape[] shapes;
    private static final String[] COLORS = {
            "Червоний", "Синій", "Зелений", "Жовтий", "Білий", "Чорний"
    };
    private static final Random random = new Random();

    public ShapeModel(int size) {
        shapes = new Shape[size];
        generateShapes();
    }

    private void generateShapes() {
        for (int i = 0; i < shapes.length; i++) {
            String color = COLORS[random.nextInt(COLORS.length)];
            int shapeType = random.nextInt(3);
            switch (shapeType) {
                case 0:
                    shapes[i] = new Rectangle(color,
                            Math.round((random.nextDouble()*10+1)*10.0)/10.0,
                            Math.round((random.nextDouble()*10+1)*10.0)/10.0);
                    break;
                case 1:
                    shapes[i] = new Triangle(color,
                            Math.round((random.nextDouble()*10+1)*10.0)/10.0,
                            Math.round((random.nextDouble()*10+1)*10.0)/10.0);
                    break;
                case 2: shapes[i] = new Circle(color,
                        Math.round((random.nextDouble()*8+1)*10.0)/10.0);
                    break;
            }
        }
    }

    public Shape[] getShapes() { return shapes; }

    public void setShapes(Shape[] shapes) { this.shapes = shapes; }

    public double calculateTotalArea() {
        double total = 0;
        if (shapes == null) return 0;
        for (Shape shape : shapes) { total += shape.calcArea(); }
        return total;
    }

    public double calculateAreaByType(Class<? extends Shape> shapeClass) {
        double total = 0;
        if (shapes == null) return 0;
        for (Shape shape : shapes) {
            if (shape.getClass().equals(shapeClass)) { total += shape.calcArea(); }
        }
        return total;
    }

    public void sortByArea() {
        if (shapes == null) return;
        Arrays.sort(shapes, Comparator.comparingDouble(Shape::calcArea));
    }

    public void sortByColor() {
        if (shapes == null) return;
        Arrays.sort(shapes, Comparator.comparing(Shape::getShapeColor));
    }
}