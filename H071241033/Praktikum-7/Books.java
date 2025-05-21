public class Books extends LibraryItem {
    private String author;

    public Books(String title, int itemID, String author) {
        super(title, itemID);
        this.author = author;
    }

    @Override
    public String getDescription() {
        return "Judul: " + title + ", Penulis: " + author + ", ID: " + itemID;
    }

    @Override
    public String borrowedItem(int days) {
    if (isBorrowed) {
        throw new IllegalArgumentException("Item sudah dipinjam dan belum dikembalikan.");
    }

    if (days > 14) {
        throw new IllegalArgumentException("Peminjaman tidak boleh lebih dari 14 hari.");
    }

    isBorrowed = true;
    return title + " dipinjam selama " + days + " hari.";
    }

    @Override
    public double calculateFine(int daysLate) {
        return daysLate * 1000; 
    }
}
