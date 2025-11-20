import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

class TranslatorTest {

    private Translator translator;

    @BeforeEach
    void setUp() {
        translator = new Translator();

        translator.addWord("hello", "привіт");
        translator.addWord("world", "світ");
        translator.addWord("java", "джава");
        translator.addWord("is", "є");
        translator.addWord("programming", "програмування");
        translator.addWord("great", "чудовий");
        translator.addWord("and", "та");
        translator.addWord("cat", "кіт");
        translator.addWord("dog", "собака");
        translator.addWord("good", "добрий");
        translator.addWord("morning", "ранок");
    }

    @Test
    @DisplayName("Додавання одного слова")
    void testAddSingleWord() {
        Translator newTranslator = new Translator();
        newTranslator.addWord("test", "тест");
        assertEquals(1, newTranslator.getDictionarySize());
    }

    @Test
    @DisplayName("Додавання декількох слів")
    void testAddMultipleWords() {
        Translator newTranslator = new Translator();
        newTranslator.addWord("cat", "кіт");
        newTranslator.addWord("dog", "собака");
        newTranslator.addWord("house", "будинок");
        assertEquals(3, newTranslator.getDictionarySize());
    }

    @Test
    @DisplayName("Додавання слова з великими літерами")
    void testAddWordWithUpperCase() {
        Translator newTranslator = new Translator();
        newTranslator.addWord("Hello", "привіт");
        String result = newTranslator.translate("hello");
        assertTrue(result.contains("привіт"));
    }

    @Test
    @DisplayName("Оновлення існуючого слова")
    void testUpdateExistingWord() {
        translator.addWord("good", "хороший");
        translator.addWord("good", "добрий");

        String result = translator.translate("good");
        assertTrue(result.contains("добрий"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Test", "TEST", "test", "TeSt"})
    @DisplayName("Додавання слів у різному регістрі")
    void testAddWordDifferentCases(String word) {
        translator.addWord(word, "тест");
        String result = translator.translate("test");
        assertTrue(result.toLowerCase().contains("тест"));
    }

    @Test
    @DisplayName("Переклад одного слова")
    void testTranslateSingleWord() {
        String result = translator.translate("cat");
        assertEquals("кіт", result);
    }

    @Test
    @DisplayName("Переклад фрази з кількох слів")
    void testTranslateMultipleWords() {
        String result = translator.translate("hello world");
        assertTrue(result.contains("привіт"));
        assertTrue(result.contains("світ"));
    }

    @Test
    @DisplayName("Переклад з розділовими знаками")
    void testTranslateWithPunctuation() {
        String result = translator.translate("Hello, world!");
        assertTrue(result.contains("привіт,"));
        assertTrue(result.contains("світ!"));
    }

    @Test
    @DisplayName("Переклад слова у верхньому регістрі")
    void testTranslateUpperCase() {
        String result = translator.translate("HELLO");
        assertTrue(result.contains("привіт"));
    }

    @Test
    @DisplayName("Переклад змішаного регістру")
    void testTranslateMixedCase() {
        String result = translator.translate("HeLLo WoRLd");
        assertTrue(result.contains("привіт"));
        assertTrue(result.contains("світ"));
    }

    @Test
    @DisplayName("Переклад невідомого слова")
    void testTranslateUnknownWord() {
        String result = translator.translate("elephant");
        assertTrue(result.contains("[elephant]"));
    }

    @Test
    @DisplayName("Переклад змішаної фрази (відомі та невідомі слова)")
    void testTranslateMixedPhrase() {
        String result = translator.translate("hello unknown world");
        assertTrue(result.contains("привіт"));
        assertTrue(result.contains("[unknown]"));
        assertTrue(result.contains("світ"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  ", "\t", "\n"})
    @DisplayName("Переклад порожньої фрази")
    void testTranslateEmptyPhrase(String phrase) {
        String result = translator.translate(phrase);
        assertTrue(result.isEmpty() || result.isBlank());
    }

    @Test
    @DisplayName("Переклад фрази з множинними пробілами")
    void testTranslateWithMultipleSpaces() {
        String result = translator.translate("hello    world   java");
        assertTrue(result.contains("привіт"));
        assertTrue(result.contains("світ"));
        assertTrue(result.contains("джава"));
    }

    @ParameterizedTest
    @CsvSource({
            "'hello world', 'привіт', 'світ'",
            "'java is great', 'джава', 'є'",
            "'good morning world', 'добрий', 'ранок'",
            "'cat and dog', 'кіт', 'собака'"
    })
    @DisplayName("Параметризований тест перекладу")
    void testTranslateParameterized(String input, String word1, String word2) {
        String result = translator.translate(input);
        assertTrue(result.contains(word1));
        assertTrue(result.contains(word2));
    }

    @Test
    @DisplayName("Переклад складної фрази")
    void testTranslateComplexPhrase() {
        String result = translator.translate("Good morning, world!");
        assertTrue(result.contains("добрий"));
        assertTrue(result.contains("ранок,"));
        assertTrue(result.contains("світ!"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"hello!", "hello?", "hello.", "hello..."})
    @DisplayName("Переклад з різною кінцевою пунктуацією")
    void testTranslateWithEndPunctuation(String input) {
        String result = translator.translate(input);
        assertTrue(result.contains("привіт"));
        assertTrue(result.matches(".*[!.?]+$"));
    }

    @Test
    @DisplayName("Розмір порожнього словника")
    void testEmptyDictionarySize() {
        Translator emptyTranslator = new Translator();
        assertEquals(0, emptyTranslator.getDictionarySize());
    }

    @Test
    @DisplayName("Розмір словника після ініціалізації")
    void testInitialDictionarySize() {
        assertEquals(11, translator.getDictionarySize());
    }

    @Test
    @DisplayName("Розмір словника після додавання слів")
    void testDictionarySizeAfterAdding() {
        int initialSize = translator.getDictionarySize();
        translator.addWord("new", "новий");
        translator.addWord("word", "слово");
        assertEquals(initialSize + 2, translator.getDictionarySize());
    }

    @Test
    @DisplayName("Розмір після оновлення існуючого слова")
    void testDictionarySizeAfterUpdate() {
        int initialSize = translator.getDictionarySize();
        translator.addWord("cat", "котик");
        assertEquals(initialSize, translator.getDictionarySize());
    }

    @Test
    @DisplayName("Інтеграційний тест: повний цикл роботи")
    void testFullWorkflow() {
        Translator newTranslator = new Translator();

        newTranslator.addWord("I", "я");
        newTranslator.addWord("love", "люблю");
        newTranslator.addWord("java", "джава");
        newTranslator.addWord("programming", "програмування");

        assertEquals(4, newTranslator.getDictionarySize());

        String result = newTranslator.translate("I love Java programming!");

        assertTrue(result.contains("я"));
        assertTrue(result.contains("люблю"));
        assertTrue(result.contains("джава"));
        assertTrue(result.contains("програмування!"));
    }

    @Test
    @DisplayName("Інтеграційний тест: складне речення з різними елементами")
    void testComplexSentence() {
        String phrase = "Hello, world! Java is great programming and cat, dog.";
        String result = translator.translate(phrase);

        assertTrue(result.contains("привіт"));
        assertTrue(result.contains("світ"));
        assertTrue(result.contains("джава"));
        assertTrue(result.contains("є"));
        assertTrue(result.contains("чудовий"));
        assertTrue(result.contains("програмування"));
        assertTrue(result.contains("та"));
        assertTrue(result.contains("кіт"));
        assertTrue(result.contains("собака"));
    }

    @Test
    @DisplayName("Тест граничних випадків: дуже коротке слово")
    void testEdgeCaseShortWord() {
        translator.addWord("a", "а");
        translator.addWord("I", "я");

        String result = translator.translate("I a cat");
        assertTrue(result.contains("я"));
        assertTrue(result.contains("а"));
        assertTrue(result.contains("кіт"));
    }

    @Test
    @DisplayName("Тест граничних випадків: дуже довге слово")
    void testEdgeCaseLongWord() {
        translator.addWord("supercalifragilisticexpialidocious", "надзвичайнофантастично");

        String result = translator.translate("supercalifragilisticexpialidocious");
        assertTrue(result.contains("надзвичайнофантастично"));
    }

    @Test
    @DisplayName("Тест спеціальних символів у фразі")
    void testSpecialCharacters() {
        String result = translator.translate("hello, world! java? programming...");
        assertTrue(result.contains("привіт,"));
        assertTrue(result.contains("світ!"));
        assertTrue(result.contains("джава?"));
        assertTrue(result.contains("програмування..."));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "hello world",
            "HELLO WORLD",
            "Hello World",
            "hElLo WoRlD"
    })
    @DisplayName("Різні варіанти регістру дають однаковий результат")
    void testCaseInsensitiveTranslation(String phrase) {
        String result = translator.translate(phrase);
        assertTrue(result.toLowerCase().contains("привіт"));
        assertTrue(result.toLowerCase().contains("світ"));
    }

    @Test
    @DisplayName("Перевірка що невідомі слова не додаються до словника")
    void testUnknownWordsNotAddedToDictionary() {
        int initialSize = translator.getDictionarySize();
        translator.translate("unknown mysterious words");
        assertEquals(initialSize, translator.getDictionarySize());
    }
}