package task2.view;

public class ShapeView {

    public void displayMenu() {
        System.out.println("\n========== ГОЛОВНЕ МЕНЮ ==========");
        System.out.println("1. Вивести всі фігури");
        System.out.println("2. Розрахувати загальну площу");
        System.out.println("3. Розрахувати площу за типом");
        System.out.println("4. Сортувати за площею");
        System.out.println("5. Сортувати за кольором");
        System.out.println("---------------------------------");
        System.out.println("6. Зберегти набір у файл");
        System.out.println("7. Завантажити набір з файлу");
        System.out.println("---------------------------------");
        System.out.println("0. Вихід");
        System.out.println("=================================");
    }

    public void displayPrompt(String prompt) {
        System.out.print(prompt + " ");
    }

    public void displayShapes(String[] shapeStrings) {
        System.out.println("========== НАБІР ФІГУР ==========");
        if (shapeStrings == null) {
            System.out.println("Набір порожній.");
        } else {
            for (String shapeInfo : shapeStrings) {
                System.out.println(shapeInfo);
            }
        }
        System.out.println("=================================\n");
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void displayError(String errorMessage) {
        System.err.println("ПОМИЛКА: " + errorMessage);
    }
}