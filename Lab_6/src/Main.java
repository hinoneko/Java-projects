import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Translator translator = new Translator();

        System.out.println("Англійсько-український перекладач");
        System.out.println("=================================\n");

        System.out.println("Наповнення словника базовими словами");
        translator.addWord("hello", "привіт");
        translator.addWord("world", "світ");
        translator.addWord("good", "добрий");
        translator.addWord("morning", "ранок");
        translator.addWord("day", "день");
        translator.addWord("evening", "вечір");
        translator.addWord("night", "ніч");
        translator.addWord("cat", "кіт");
        translator.addWord("dog", "собака");
        translator.addWord("house", "будинок");
        translator.addWord("book", "книга");
        translator.addWord("water", "вода");
        translator.addWord("love", "любов");
        translator.addWord("life", "життя");
        translator.addWord("friend", "друг");
        System.out.println("Всього слів у словнику: " + translator.getDictionarySize());

        while (true) {
            System.out.println("\nМеню");
            System.out.println("1. Додати слово до словника");
            System.out.println("2. Переклад фрази");
            System.out.println("3. Показати словник");
            System.out.println("4. Вихід");
            System.out.print("Виберіть опцію (1-4): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.print("\nВведіть англійське слово: ");
                    String engWord = scanner.nextLine().trim();
                    System.out.print("Введіть український переклад: ");
                    String ukrWord = scanner.nextLine().trim();

                    if (!engWord.isEmpty() && !ukrWord.isEmpty()) {
                        translator.addWord(engWord, ukrWord);
                    } else {
                        System.out.println("Помилка: слова не можуть бути порожніми!");
                    }
                    break;

                case "2":
                    System.out.print("Введіть фразу англійською мовою: ");
                    String phrase = scanner.nextLine();

                    if (!phrase.trim().isEmpty()) {
                        String translation = translator.translate(phrase);
                        System.out.println("\nОригінал: " + phrase);
                        System.out.println("Переклад: " + translation);
                        System.out.println("\nПримітка: слова в [дужках] не знайдені у словнику");
                    } else {
                        System.out.println("Помилка: фраза не може бути порожньою!");
                    }
                    break;

                case "3":
                    translator.showDictionary();
                    System.out.println("Всього слів у словнику: " + translator.getDictionarySize());
                    break;

                case "4":
                    scanner.close();
                    return;

                default:
                    System.out.println("Невірний вибір! Спробуйте ще раз.");
            }
        }
    }
}