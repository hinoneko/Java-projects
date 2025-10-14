package controller;

import model.*;
import view.ShapeView;

public class ShapeController {
    private ShapeModel model;
    private ShapeView view;

    public ShapeController(ShapeModel model, ShapeView view) {
        this.model = model;
        this.view = view;
    }

    public void run() {
        view.displayMessage("1. ПОЧАТКОВИЙ НАБІР ДАНИХ:");
        view.displayShapes(model.getShapes());

        view.displayMessage("2. СУМАРНА ПЛОЩА ВСІХ ФІГУР:");
        double totalArea = model.calculateTotalArea();
        view.displayTotalArea(totalArea);

        view.displayMessage("3. СУМАРНА ПЛОЩА ЗА ТИПАМИ:");
        double rectangleArea = model.calculateAreaByType(Rectangle.class);
        view.displayAreaByType("Rectangle", rectangleArea);

        double triangleArea = model.calculateAreaByType(Triangle.class);
        view.displayAreaByType("Triangle", triangleArea);

        double circleArea = model.calculateAreaByType(Circle.class);
        view.displayAreaByType("Circle", circleArea);

        view.displayMessage("\n4. СОРТУВАННЯ ЗА ПЛОЩЕЮ (зростання):");
        model.sortByArea();
        view.displayShapes(model.getShapes());

        view.displayMessage("5. СОРТУВАННЯ ЗА КОЛЬОРОМ:");
        model.sortByColor();
        view.displayShapes(model.getShapes());
    }
}