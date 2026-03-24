package VendingMachine.model;
public class Product {
    private final String code, name;
    private final double price;
    private int quantity;
    public Product(String code, String name, double price, int quantity) { this.code = code; this.name = name; this.price = price; this.quantity = quantity; }
    public synchronized boolean dispense() { if (quantity <= 0) return false; quantity--; return true; }
    public void restock(int qty) { quantity += qty; }
    public String getCode() { return code; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public boolean isAvailable() { return quantity > 0; }
    @Override public String toString() { return code + ":" + name + "($" + String.format("%.2f", price) + ",qty=" + quantity + ")"; }
}
