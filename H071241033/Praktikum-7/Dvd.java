public class Dvd extends LibraryItem {
    private int duration;

    public Dvd(String title, int itemID, int duration) {
        super(title, itemID);
        this.duration = duration;
    }

    @Override
    public String getDescription() {
        return "DVD: "+ title + ", durasi " + duration + " menit. " + "ID: " + itemID;
    }

    @Override
    public String borrowedItem(int days) {
    if (isBorrowed) {
        throw new IllegalArgumentException("Item sudah dipinjam dan belum dikembalikan.");
    }

    if (days > 7) {
        throw new IllegalArgumentException("Peminjaman tidak boleh lebih dari 7 hari.");
    }

    isBorrowed = true;
    return title + " dipinjam selama " + days + " hari.";
    }

    @Override
    public double calculateFine(int daysLate) {
        return daysLate * 250000; 
    }
}

