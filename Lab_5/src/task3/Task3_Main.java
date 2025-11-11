package task3;

import task3.io.DecryptReader;
import task3.io.EncryptWriter;

import java.io.*;
import java.util.Scanner;

public class Task3_Main {

    public void encryptFile(String source, String dest, char key) throws IOException {
        try (Reader reader = new BufferedReader(new FileReader(source));
             Writer writer = new EncryptWriter(new BufferedWriter(new FileWriter(dest)), key)) {

            int c;
            while ((c = reader.read()) != -1) {
                writer.write(c);
            }
        }
    }

    public void decryptFile(String source, String dest, char key) throws IOException {
        try (Reader reader = new DecryptReader(new BufferedReader(new FileReader(source)), key);
             Writer writer = new BufferedWriter(new FileWriter(dest))) {

            int c;
            while ((c = reader.read()) != -1) {
                writer.write(c);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Task3_Main task3 = new Task3_Main();

        try {
            System.out.println("Оберіть дію: (1) Шифрувати, (2) Дешифрувати");
            int choice = Integer.parseInt(scanner.nextLine());

            System.out.println("Введіть символ-ключ (1 символ):");
            char key = scanner.nextLine().charAt(0);

            System.out.println("Введіть шлях до вхідного файлу:");
            String inputFile = scanner.nextLine();

            System.out.println("Введіть шлях до вихідного файлу:");
            String outputFile = scanner.nextLine();

            if (choice == 1) {
                task3.encryptFile(inputFile, outputFile, key);
                System.out.println("Файл успішно зашифровано.");
            } else if (choice == 2) {
                task3.decryptFile(inputFile, outputFile, key);
                System.out.println("Файл успішно дешифровано.");
            } else {
                System.out.println("Невірний вибір.");
            }
        } catch (IOException e) {
            System.err.println("Помилка роботи з файлом: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Помилка введення: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}