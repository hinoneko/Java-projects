public class MainTest {
    private static String[] testWords = {"hello", "world", "java", "code", "test", "abc", "programming", "unique"};

    public static String[] getTestWords() {
        return testWords;
    }

    public static String[] runTest() {
        return Main.findUniqueWords(testWords);
    }
}
