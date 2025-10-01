import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final CuratorJournal journal = new CuratorJournal();

    public static void main(String[] args) {
        System.out.println("Журнал куратора");

        while (true) {
            displayMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> addNewRecord();
                case "2" -> journal.displayAllRecords();
                case "3" -> MainTest.runAllTests();
                case "4" -> {
                    System.out.println("До побачення!");
                    return;
                }
                default -> System.out.println("Оберіть пункт від 1 до 4");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n1. Додати запис");
        System.out.println("2. Показати записи");
        System.out.println("3. Запустити тести");
        System.out.println("4. Вийти");
        System.out.print("Вибір: ");
    }

    private static void addNewRecord() {
        System.out.println("\nДодавання студента");

        System.out.print("Прізвище: ");
        String surname = scanner.nextLine().trim();

        System.out.print("Ім'я: ");
        String name = scanner.nextLine().trim();

        String birthDateStr = inputWithValidation(
                "Дата народження (ДД.ММ.РРРР): ",
                Validator::validateDate,
                "Некоректна дата"
        );
        LocalDate birthDate = LocalDate.parse(birthDateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        String phone = inputWithValidation(
                "Телефон (+380XXXXXXXXX): ",
                Validator::validatePhone,
                "Некоректний телефон"
        );

        System.out.println("Адреса:");
        System.out.print("Вулиця: ");
        String street = scanner.nextLine().trim();

        System.out.print("Будинок: ");
        String house = scanner.nextLine().trim();

        System.out.print("Квартира: ");
        String apartment = scanner.nextLine().trim();

        Address address = new Address(street, house, apartment);
        StudentRecord record = new StudentRecord(surname, name, birthDate, phone, address);
        journal.addRecord(record);

        System.out.println("Запис додано. Всього: " + journal.getRecordsCount());
    }

    private static String inputWithValidation(String input_msg, ValidationFunction validator, String error_msg) {
        while (true) {
            System.out.print(input_msg);
            String input = scanner.nextLine().trim();

            if (validator.validate(input)) {
                return input;
            }
            System.out.println(error_msg);
        }
    }

    @FunctionalInterface
    interface ValidationFunction {
        boolean validate(String input);
    }
}