package task1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileWordCounter {

    public String findLineWithMostWords(String filePath) throws IOException {
        String lineWithMostWords = "";
        int maxWordCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] words = currentLine.trim().split("\\s+");
                int currentWordCount = words.length;

                if (currentLine.trim().isEmpty()) {
                    currentWordCount = 0;
                }

                if (currentWordCount > maxWordCount) {
                    maxWordCount = currentWordCount;
                    lineWithMostWords = currentLine;
                }
            }
        }
        return lineWithMostWords;
    }
}