import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class Validator {
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+380\\d{9}$");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static boolean validateDate(String date) {
        if (date == null) {
            return false;
        }
        try {
            LocalDate parsedDate = LocalDate.parse(date, DATE_FORMATTER);
            LocalDate now = LocalDate.now();
            LocalDate minDate = now.minusYears(100);
            return parsedDate.isBefore(now) && parsedDate.isAfter(minDate);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean validatePhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }
}

