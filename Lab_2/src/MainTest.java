import java.time.LocalDate;

public class MainTest {

    public static void runAllTests() {

        TestRunner testRunner = new TestRunner();

        testRunner.runTest("Validator.validateDate - правильна дата", () -> testValidatorDate());
        testRunner.runTest("Validator.validateDate - неправильна дата", () -> testValidatorDateInvalid());
        testRunner.runTest("Validator.validatePhone - правильний телефон", () -> testValidatorPhone());
        testRunner.runTest("Validator.validatePhone - неправильний телефон", () -> testValidatorPhoneInvalid());

        testRunner.runTest("Тест адреси", () -> testAddress());

        testRunner.runTest("Тест студента", () -> testStudentRecord());

        testRunner.runTest("Тест журналу куратора: додавання запису", () -> testCuratorJournalAdd());
        testRunner.runTest("Тест журналу куратора: порожній журнал", () -> testCuratorJournalDisplayEmpty());
        testRunner.runTest("Тест журналу куратора: журнал з записами", () -> testCuratorJournalDisplayWithRecords());

        testRunner.printResults();
    }


    private static boolean testValidatorDate() {
        return Validator.validateDate("15.03.1990") &&
                Validator.validateDate("01.01.2000") &&
                Validator.validateDate("31.12.2020");
    }

    private static boolean testValidatorDateInvalid() {
        return !Validator.validateDate("32.01.1990") &&
                !Validator.validateDate("15.13.1990") &&
                !Validator.validateDate("15.03.2030") &&
                !Validator.validateDate("invalid") &&
                !Validator.validateDate(null);
    }

    private static boolean testValidatorPhone() {
        return Validator.validatePhone("+380501234567") &&
                Validator.validatePhone("+380671234567") &&
                Validator.validatePhone("+380441234567");
    }

    private static boolean testValidatorPhoneInvalid() {
        return !Validator.validatePhone("380501234567") &&
                !Validator.validatePhone("+38050123456") &&
                !Validator.validatePhone("+3805012345678") &&
                !Validator.validatePhone("+38050123456a") &&
                !Validator.validatePhone("invalid") &&
                !Validator.validatePhone(null);
    }


    private static boolean testAddress() {
        Address address = new Address("Шевченка", "15", "42");

        return "Шевченка".equals(address.getStreet()) &&
                "15".equals(address.getHouse()) &&
                "42".equals(address.getApartment()) &&
                "Шевченка, 15, 42".equals(address.toString());
    }


    private static boolean testStudentRecord() {
        Address address = new Address("Шевченка", "15", "42");
        LocalDate birthDate = LocalDate.of(1990, 3, 15);
        StudentRecord record = new StudentRecord("Іваненко", "Іван", birthDate, "+380501234567", address);

        return "Іваненко".equals(record.getSurname()) &&
                "Іван".equals(record.getName()) &&
                birthDate.equals(record.getBirthDate()) &&
                "+380501234567".equals(record.getPhone()) &&
                address.equals(record.getAddress()) &&
                record.toString().contains("Іваненко") &&
                record.toString().contains("Іван");
    }


    private static boolean testCuratorJournalAdd() {
        CuratorJournal testJournal = new CuratorJournal();

        if (testJournal.getRecordsCount() != 0) return false;

        Address address = new Address("Шевченка", "15", "42");
        LocalDate birthDate = LocalDate.of(1990, 3, 15);
        StudentRecord record = new StudentRecord("Іваненко", "Іван", birthDate, "+380501234567", address);

        testJournal.addRecord(record);

        return testJournal.getRecordsCount() == 1;
    }

    private static boolean testCuratorJournalDisplayEmpty() {
        CuratorJournal testJournal = new CuratorJournal();

        try {
            testJournal.displayAllRecords();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean testCuratorJournalDisplayWithRecords() {
        CuratorJournal testJournal = new CuratorJournal();

        Address address1 = new Address("Шевченка", "15", "42");
        LocalDate birthDate1 = LocalDate.of(1990, 3, 15);
        StudentRecord record1 = new StudentRecord("Іваненко", "Іван", birthDate1, "+380501234567", address1);

        Address address2 = new Address("Франка", "20", "10");
        LocalDate birthDate2 = LocalDate.of(1995, 7, 22);
        StudentRecord record2 = new StudentRecord("Петренко", "Петро", birthDate2, "+380671234567", address2);

        testJournal.addRecord(record1);
        testJournal.addRecord(record2);

        try {
            testJournal.displayAllRecords();
            return testJournal.getRecordsCount() == 2;
        } catch (Exception e) {
            return false;
        }
    }
}
