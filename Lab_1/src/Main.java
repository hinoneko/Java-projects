import java.util.*;

public class Main {

    public static String[] findUniqueWords(String[] testWords) {
        ArrayList<String> result = new ArrayList<>();

        for (String word : testWords) {
            if (hasUniqueChars(word)) {
                result.add(word);
            }
        }
        return result.toArray(new String[0]);
    }

    private static boolean hasUniqueChars(String word) {
        Set<Character> seenChars = new HashSet<>();

        for (char letter : word.toCharArray()) {
            if (!seenChars.add(letter)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String[] testWords = MainTest.getTestWords();
        String[] result = MainTest.runTest();

        System.out.println("Вхідні слова:");
        for (String word : testWords) {
            System.out.print(word + " ");
        }

        System.out.println("\n\nСлова з унікальними символами:");
        for (String word : result) {
            System.out.print(word + " ");
        }
    }
}
