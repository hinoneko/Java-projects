package task1;

import java.util.Scanner;
import java.io.IOException;

public class Task1_Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FileWordCounter counter = new FileWordCounter();

        try {
            System.out.println("Введіть шлях до файлу:");
            String filePath = scanner.nextLine();

            String resultLine = counter.findLineWithMostWords(filePath);

            if (resultLine.isEmpty()) {
                System.out.println("Файл порожній або не містить слів.");
            } else {
                System.out.println("Рядок з максимальною кількістю слів:");
                System.out.println(resultLine);
            }

        } catch (IOException e) {
            System.err.println("Помилка читання файлу: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Сталася непередбачена помилка: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}