package OnlineBookstore.model;
public class CartItem {
    private final Book book; private int quantity;
    public CartItem(Book book, int quantity) { this.book = book; this.quantity = quantity; }
    public Book getBook() { return book; }
    public int getQuantity() { return quantity; }
    public void addQuantity(int qty) { quantity += qty; }
    @Override public String toString() { return book.getTitle() + " x" + quantity; }
}
