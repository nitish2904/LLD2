package OnlineBookstore.model;
public class Book {
    private final String isbn, title, author; private final double basePrice; private int stock;
    public Book(String isbn, String title, String author, double basePrice, int stock) { this.isbn = isbn; this.title = title; this.author = author; this.basePrice = basePrice; this.stock = stock; }
    public synchronized boolean decrementStock(int qty) { if (stock < qty) return false; stock -= qty; return true; }
    public synchronized void incrementStock(int qty) { stock += qty; }
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public double getBasePrice() { return basePrice; }
    public int getStock() { return stock; }
    @Override public String toString() { return title + "($" + String.format("%.2f", basePrice) + ", stock=" + stock + ")"; }
}
