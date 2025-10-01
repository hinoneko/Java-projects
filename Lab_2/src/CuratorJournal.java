import java.util.ArrayList;
import java.util.List;

public class CuratorJournal {
    private final List<StudentRecord> records = new ArrayList<>();

    public void addRecord(StudentRecord record) {
        records.add(record);
    }

    public void displayAllRecords() {
        if (records.isEmpty()) {
            System.out.println("Журнал порожній");
            return;
        }
        
        System.out.println("Всього записів: " + records.size());
        for (int i = 0; i < records.size(); i++) {
            System.out.println("\nЗапис " + (i + 1) + ":");
            System.out.println(records.get(i));
        }
    }

    public int getRecordsCount() {
        return records.size();
    }
}

