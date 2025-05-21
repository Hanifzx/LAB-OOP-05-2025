import java.util.ArrayList;
import java.util.List;

public class Member {
    public String name;
    public int memberId;
    public List<LibraryItem> borrowedItems = new ArrayList<>();

    public Member(String name, int memberId) {
        this.name = name;
        this.memberId = memberId;
    }

    public String borrow(LibraryItem item,  int days){
        if (item.isBorrowed){
            throw new IllegalArgumentException("Item sudah di pinjam.");
        }
        item.borrowedItem(days);
        borrowedItems.add(item);

        return "Item [" + item.title + "] berhasil dipinjam selama " + days + " hari";
    }

    public String returnItem(LibraryItem item, int daysLate) {
    if (!item.isBorrowed) {
        throw new IllegalArgumentException("Item tidak sedang dipinjam.");
    }

    if (!borrowedItems.contains(item)) {
        throw new IllegalArgumentException("Item ini tidak dipinjam oleh anggota ini.");
    }

    double fine = item.calculateFine(daysLate);
    item.isBorrowed = false;
    borrowedItems.remove(item);

    return "Item [" + item.title + "] berhasil dikembalikan dengan denda: Rp " + String.format("%,.2f", fine);
}


    public void getBorrowwedItem(){
        if (borrowedItems.isEmpty()){
            throw new IllegalArgumentException("Tidak ada item yang dipinjam");
        }

        System.out.println("+------+-------------------+");
        System.out.println("|  ID  |       Judul       |");
        System.out.println("+------+-------------------+");

        // Print setiap item yang dipinjam
        for (LibraryItem item : borrowedItems) {
            System.out.printf("| %-4d | %-17s |\n", item.itemID, item.title);
        }

        // Footer tabel
        System.out.println("+------+-------------------+");
    }
}
