package task2;

import task2.controller.ShapeController;
import task2.model.ShapeModel;
import task2.view.ShapeView;

public class Task2_Main {
    public static void main(String[] args) {
        ShapeModel model = new ShapeModel(10);
        ShapeView view = new ShapeView();
        ShapeController controller = new ShapeController(model, view);

        controller.run();
    }
}