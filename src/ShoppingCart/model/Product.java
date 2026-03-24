package ShoppingCart.model;
public class Product {
    private final String productId, name, category;
    private final double price;
    private int stockQuantity;
    public Product(String productId, String name, String category, double price, int stockQuantity) {
        this.productId = productId; this.name = name; this.category = category; this.price = price; this.stockQuantity = stockQuantity;
    }
    public synchronized boolean reserveStock(int qty) { if (qty > stockQuantity) return false; stockQuantity -= qty; return true; }
    public synchronized void restoreStock(int qty) { stockQuantity += qty; }
    public String getProductId() { return productId; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public int getStockQuantity() { return stockQuantity; }
    @Override public String toString() { return name + "($" + String.format("%.2f", price) + ", stock=" + stockQuantity + ")"; }
}
