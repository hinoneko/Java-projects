package task2.controller;

import task2.model.*;
import task2.util.ShapeFileSerializer;
import task2.view.ShapeView;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ShapeController {
    private ShapeModel model;
    private ShapeView view;
    private ShapeFileSerializer serializer;
    private Scanner scanner;

    public ShapeController(ShapeModel model, ShapeView view) {
        this.model = model;
        this.view = view;
        this.serializer = new ShapeFileSerializer();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        boolean running = true;
        showShapes();

        while (running) {
            view.displayMenu();
            int choice = getMenuChoice();

            switch (choice) {
                case 1:
                    showShapes();
                    break;
                case 2:
                    showTotalArea();
                    break;
                case 3:
                    showAreaByType();
                    break;
                case 4:
                    sortShapesByArea();
                    break;
                case 5:
                    sortShapesByColor();
                    break;
                case 6:
                    saveShapes();
                    break;
                case 7:
                    loadShapes();
                    break;
                case 0:
                    running = false; view.displayMessage("Роботу завершено.");
                    break;
                default:
                    view.displayError("Невірний вибір. Спробуйте ще раз.");
                    break;
            }
        }
        scanner.close();
    }

    private int getMenuChoice() {
        view.displayPrompt("Ваш вибір:");
        try {
            int choice = scanner.nextInt();
            scanner.nextLine();
            return choice;
        } catch (InputMismatchException e) {
            scanner.nextLine();
            return -1;
        }
    }

    private String getInputString(String prompt) {
        view.displayPrompt(prompt);
        return scanner.nextLine();
    }

    private void showShapes() {
        view.displayMessage("\n1. ПОТОЧНИЙ НАБІР ДАНИХ:");
        Shape[] shapes = model.getShapes();
        String[] shapeStrings = null;

        if (shapes != null && shapes.length > 0) {
            shapeStrings = new String[shapes.length];
            for (int i = 0; i < shapes.length; i++) {
                shapeStrings[i] = String.format("%d. %s", i + 1, shapes[i].toString());
            }
        }
        view.displayShapes(shapeStrings);
    }

    private void showTotalArea() {
        view.displayMessage("\n2. СУМАРНА ПЛОЩА ВСІХ ФІГУР:");
        double totalArea = model.calculateTotalArea();
        String formattedArea = String.format("Сумарна площа всіх фігур: %.2f%n", totalArea);
        view.displayMessage(formattedArea);
    }

    private void showAreaByType() {
        view.displayMessage("\n3. СУМАРНА ПЛОЩА ЗА ТИПАМИ:");

        double rectArea = model.calculateAreaByType(Rectangle.class);
        double triArea = model.calculateAreaByType(Triangle.class);
        double circArea = model.calculateAreaByType(Circle.class);

        view.displayMessage(String.format("Сумарна площа фігур типу %s: %.2f", "Rectangle", rectArea));
        view.displayMessage(String.format("Сумарна площа фігур типу %s: %.2f", "Triangle", triArea));
        view.displayMessage(String.format("Сумарна площа фігур типу %s: %.2f", "Circle", circArea));
    }

    private void sortShapesByArea() {
        view.displayMessage("\n4. СОРТУВАННЯ ЗА ПЛОЩЕЮ (зростання):");
        model.sortByArea();
        showShapes();
    }

    private void sortShapesByColor() {
        view.displayMessage("\n5. СОРТУВАННЯ ЗА КОЛЬОРОМ:");
        model.sortByColor();
        showShapes();
    }

    private void saveShapes() {
        String filePath = getInputString("Введіть ім'я файлу для збереження:");
        try {
            serializer.saveShapesToFile(model.getShapes(), filePath);
            view.displayMessage("Набір успішно збережено у файл " + filePath);
        } catch (IOException e) {
            view.displayError("Помилка збереження файлу: " + e.getMessage());
        }
    }

    private void loadShapes() {
        String filePath = getInputString("Введіть ім'я файлу для завантаження:");
        try {
            Shape[] loadedShapes = serializer.loadShapesFromFile(filePath);
            model.setShapes(loadedShapes);
            view.displayMessage("Набір успішно завантажено з файлу " + filePath);
            showShapes();
        } catch (IOException e) {
            view.displayError("Помилка завантаження файлу: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            view.displayError("Помилка: клас не знайдено при десеріалізації. " + e.getMessage());
        } catch (Exception e) {
            view.displayError("Сталася непередбачена помилка: " + e.getMessage());
        }
    }
}