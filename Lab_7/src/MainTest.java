import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Collections;
import java.util.Arrays;


class MainTest {

    private Main mainInstance;

    @BeforeEach
    void setUp() {
        mainInstance = new Main();
    }

    @Test
    void testStandardCase() {
        List<String> input = Arrays.asList(
                "hello",
                "world",
                "unique",
                "stream",
                "programming",
                "abc",
                "java"
        );

        List<String> expected = List.of("world", "stream", "abc");

        List<String> actual = mainInstance.findWordsWithDistinctChars(input);

        assertEquals(expected, actual, "Мають бути знайдені слова без повторів символів.");
    }

    @Test
    void testNoneUnique() {
        List<String> input = List.of("apple", "book", "mississippi");
        List<String> expected = Collections.emptyList();

        assertEquals(expected, mainInstance.findWordsWithDistinctChars(input),
                "Має бути повернений порожній список.");
    }

    @Test
    void testEmptyAndNullInput() {
        assertTrue(mainInstance.findWordsWithDistinctChars(Collections.emptyList()).isEmpty(),
                "Порожній список має дати порожній результат.");

        assertNotNull(mainInstance.findWordsWithDistinctChars(null),
                "Результат для null-входу не повинен бути null.");
        assertTrue(mainInstance.findWordsWithDistinctChars(null).isEmpty(),
                "Результат для null-входу має бути порожнім списком.");
    }

    @Test
    void testEdgeCases() {
        List<String> input = List.of("a", "aa", "", "b-c", "Aa");
        List<String> expected = List.of("a", "", "b-c", "Aa");

        List<String> actual = mainInstance.findWordsWithDistinctChars(input);

        assertEquals(expected, actual);
    }
}