import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class Main {

    private static final Predicate<String> HAS_ONLY_DISTINCT_CHARS = word ->
            word != null && word.length() == word.chars().distinct().count();

    public List<String> findWordsWithDistinctChars(List<String> words) {
        if (words == null) {
            return List.of();
        }

        return words.stream()
                .filter(HAS_ONLY_DISTINCT_CHARS)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        Main tasks = new Main();
        Scanner scanner = new Scanner(System.in);
        String choice;
        List<String> inputWords = List.of();

        while (true) {
            System.out.println("\nВибір режиму виконання");
            System.out.println("1. Демонстраційний режим");
            System.out.println("2. Інтерактивний ввід");
            System.out.println("3. Вихід з програми");
            System.out.print("Оберіть опцію (1-3): ");

            choice = scanner.nextLine().trim();

            if ("3".equals(choice)) {
                scanner.close();
                return;
            }

            if ("1".equals(choice)) {
                System.out.println("\nРежим демонстрації:");
                inputWords = List.of(
                        "hello",
                        "world",
                        "unique",
                        "stream",
                        "abc",
                        "java"
                );
            } else if ("2".equals(choice)) {
                System.out.println("\nРежим інтерактивного вводу:");
                System.out.println("Введіть слова, розділені комами або пробілами:");
                String inputLine = scanner.nextLine();

                inputWords = Arrays.stream(inputLine.split("[,\\s]+"))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toList());
            } else {
                System.out.println("\nНекоректний вибір. Спробуйте ще раз.");
                continue;
            }

            System.out.println("Вхідний список слів: " + inputWords);

            List<String> result = tasks.findWordsWithDistinctChars(inputWords);

            System.out.println("\nСлова, що складаються тільки з різних символів: " + result);

        }
    }
}