import java.util.HashMap;
import java.util.Map;


class Translator {
    private final Map<String, String> dictionary;

    public Translator() {
        this.dictionary = new HashMap<>();
    }

    public void addWord(String englishWord, String ukrainianWord) {
        if (englishWord != null && ukrainianWord != null && !englishWord.trim().isEmpty() && !ukrainianWord.trim().isEmpty()) {
            dictionary.put(englishWord.toLowerCase(), ukrainianWord);
        }
    }

    public String translate(String phrase) {
        if (phrase == null || phrase.trim().isEmpty()) {
            return "";
        }

        String[] words = phrase.split("\\s+");
        StringBuilder translation = new StringBuilder();

        for (String word : words) {
            String originalWord = word;

            String cleanWord = originalWord.replaceAll("[^a-zA-Z]", "").toLowerCase();

            String punctuation = originalWord.length() > cleanWord.length()
                    ? originalWord.substring(cleanWord.length())
                    : "";

            String translatedWord = dictionary.get(cleanWord);

            if (translatedWord != null) {
                translation.append(translatedWord);
                translation.append(punctuation);
            } else {
                translation.append("[").append(originalWord).append("]");
            }

            translation.append(" ");
        }

        return translation.toString().trim();
    }

    public void showDictionary() {
        System.out.println("\nПоточний словник");
        if (dictionary.isEmpty()) {
            System.out.println("Словник порожній.");
        } else {
            dictionary.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(entry ->
                            System.out.printf("%s -> %s\n", entry.getKey(), entry.getValue())
                    );
        }
    }

    public int getDictionarySize() {
        return dictionary.size();
    }
}