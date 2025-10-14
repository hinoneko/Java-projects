package view;

import model.Shape;

public class ShapeView {

    public void displayShapes(Shape[] shapes) {
        System.out.println("========== НАБІР ФІГУР ==========");
        for (int i = 0; i < shapes.length; i++) {
            System.out.printf("%d. %s%n", i + 1, shapes[i].toString());
        }
        System.out.println("=================================\n");
    }

    public void displayTotalArea(double area) {
        System.out.printf("Сумарна площа всіх фігур: %.2f%n%n", area);
    }

    public void displayAreaByType(String shapeType, double area) {
        System.out.printf("Сумарна площа фігур типу %s: %.2f%n", shapeType, area);
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
}