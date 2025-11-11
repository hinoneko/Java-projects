package task2.util;

import task2.model.Shape;
import java.io.*;

public class ShapeFileSerializer {

    public void saveShapesToFile(Shape[] shapes, String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(shapes);
        }
    }

    public Shape[] loadShapesFromFile(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (Shape[]) ois.readObject();
        }
    }
}