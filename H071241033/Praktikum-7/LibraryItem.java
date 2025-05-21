public abstract class LibraryItem {
    
    protected String title;
    protected int itemID;
    protected boolean isBorrowed;
    
    public LibraryItem(String title, int itemID) {
        this.title = title;
        this.itemID = itemID;
        this.isBorrowed = false;
    }

    public abstract String getDescription();

    public abstract String borrowedItem(int days);

    public abstract double calculateFine(int daysLate);

    public String returnItem(){
        isBorrowed = false;
        return title + " dikembalikan";
    }
}