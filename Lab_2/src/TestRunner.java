import java.util.List;
import java.util.ArrayList;

public class TestRunner {
    private int testsPassed = 0;
    private int testsFailed = 0;
    private List<String> failedTests = new ArrayList<>();
    
    public void runTest(String testName, TestFunction test) {
        try {
            boolean result = test.run();
            if (result) {
                testsPassed++;
                System.out.println("Пройдено: " + testName);
            } else {
                testsFailed++;
                failedTests.add(testName);
                System.out.println("Провалено: " + testName);
            }
        } catch (Exception e) {
            testsFailed++;
            failedTests.add(testName + " (Помилка: " + e.getMessage() + ")");
            System.out.println("Провалено: " + testName + " (Помилка: " + e.getMessage() + ")");
        }
    }
    
    public void printResults() {
        System.out.println("\nРезультати тестів:");
        System.out.println("Пройдено: " + testsPassed);
        System.out.println("Провалено: " + testsFailed);
        System.out.println("Всього: " + (testsPassed + testsFailed));
        
        if (testsFailed > 0) {
            System.out.println("\nПровалені тести:");
            for (String failedTest : failedTests) {
                System.out.println("  - " + failedTest);
            }
        }
        
        if (testsFailed == 0) {
            System.out.println("\nВсі тести пройшли успішно!");
        }
    }
    
    @FunctionalInterface
    public interface TestFunction {
        boolean run();
    }
}
