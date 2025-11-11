package task4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task4_Main {
    public static String fetchHtmlContent(String urlString) throws IOException {
        StringBuilder content = new StringBuilder();
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    public static Map<String, Integer> countHtmlTags(String html) {
        Map<String, Integer> tagCounts = new HashMap<>();

        Pattern tagPattern = Pattern.compile("<([a-zA-Z0-9]+)");
        Matcher matcher = tagPattern.matcher(html);

        while (matcher.find()) {
            String tag = matcher.group(1).toLowerCase();
            tagCounts.put(tag, tagCounts.getOrDefault(tag, 0) + 1);
        }
        return tagCounts;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введіть URL сторінки для аналізу (наприклад, https://www.google.com):");
        String urlString = scanner.nextLine();

        try {
            String html = fetchHtmlContent(urlString);

            Map<String, Integer> counts = countHtmlTags(html);

            if (counts.isEmpty()) {
                System.out.println("Теги не знайдено або не вдалося завантажити сторінку.");
                return;
            }

            System.out.println("\nРЕЗУЛЬТАТИ (СОРТУВАННЯ ЗА НАЗВОЮ ТЕГУ)");
            Map<String, Integer> sortedByName = new TreeMap<>(counts);
            for (Map.Entry<String, Integer> entry : sortedByName.entrySet()) {
                System.out.printf("%-10s : %d%n", entry.getKey(), entry.getValue());
            }

            System.out.println("\nРЕЗУЛЬТАТИ (СОРТУВАННЯ ЗА ЧАСТОТОЮ)");
            List<Map.Entry<String, Integer>> list = new ArrayList<>(counts.entrySet());

            list.sort(Map.Entry.comparingByValue());

            for (Map.Entry<String, Integer> entry : list) {
                System.out.printf("%-10s : %d%n", entry.getKey(), entry.getValue());
            }

        } catch (IOException e) {
            System.err.println("Помилка підключення або читання URL: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Сталася непередбачена помилка: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}