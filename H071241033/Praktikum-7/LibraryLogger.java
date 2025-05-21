import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LibraryLogger {
    private List<String> logs = new ArrayList<>();

    public String logActivity(String activity) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String logEntry = timestamp + " " + activity;
        logs.add(logEntry);
        return logEntry;
    }

    public String getLogs() {
        return String.join("\n", logs);
    }

    public void clearLogs() {
        logs.clear();
    }

    public String logAddItem(String title, int itemID, String itemType) {
        return logActivity("Item ditambahkan:  \"" + title + "\" (ID: " + itemID + ")");
    }

    public String logAddMember(String name, int memberId) {
        return logActivity("Anggota ditambahkan: \"" + name + "\" (ID: " + memberId + ")");
    }
}
