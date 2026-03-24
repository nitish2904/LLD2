package ShoppingCart.model;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
public class Cart {
    private final String userId;
    private final Map<String, CartItem> items = new ConcurrentHashMap<>();
    public Cart(String userId) { this.userId = userId; }
    public void addItem(Product product, int qty) {
        items.merge(product.getProductId(), new CartItem(product, qty), (old, n) -> { old.setQuantity(old.getQuantity() + qty); return old; });
    }
    public void removeItem(String productId) { items.remove(productId); }
    public void clear() { items.clear(); }
    public Collection<CartItem> getItems() { return items.values(); }
    public double getTotal() { return items.values().stream().mapToDouble(CartItem::getSubtotal).sum(); }
    public boolean isEmpty() { return items.isEmpty(); }
    public String getUserId() { return userId; }
    @Override public String toString() { return "Cart[" + userId + " | " + items.size() + " items | $" + String.format("%.2f", getTotal()) + "]"; }
}
