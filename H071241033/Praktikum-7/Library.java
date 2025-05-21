import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Library {
    private List<LibraryItem> items = new ArrayList<>();
    private List<Member> members = new ArrayList<>();
    private LibraryLogger logger;

    public Library(LibraryLogger logger) {
        this.logger = logger;
    }

    public void addMember(Member member) {
        members.add(member);
        logger.logAddMember(member.name, member.memberId); // Catat penambahan member
    }

    public String addItem(LibraryItem item) {
        items.add(item);
        logger.logAddItem(item.title, item.itemID, item.getClass().getSimpleName()); // Catat penambahan item
        return item.title + " berhasil ditambahkan";
    }

    public LibraryItem findItemById(int itemId) {
        return items.stream()
                .filter(item -> item.itemID == itemId)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Item dengan ID " + itemId + " tidak ditemukan."));
    }

    public Member findMemberById(int memberId) {
        return members.stream()
                .filter(m -> m.memberId == memberId)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Anggota dengan ID " + memberId + " tidak ditemukan."));
    }

    public LibraryLogger getLogger() {
        return logger;
    }

    public String getLibraryStatus() {
        StringBuilder sb = new StringBuilder();
        sb.append("+------+---------+-----------+\n");
        sb.append("| ID   | Judul   | Status    |\n");
        sb.append("+------+---------+-----------+\n");

        for (LibraryItem item : items) {
            sb.append(String.format("| %-4d | %-7s | %-9s |\n",
                    item.itemID, item.title, item.isBorrowed ? "Dipinjam" : "Tersedia"));
        }

        sb.append("+------+---------+-----------+");
        return sb.toString();
    }

    public String getAllLogs() {
        return logger.getLogs();
    }
}
