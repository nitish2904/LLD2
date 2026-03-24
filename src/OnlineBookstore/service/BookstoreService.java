package OnlineBookstore.service;
import OnlineBookstore.model.*;
import OnlineBookstore.observer.OrderObserver;
import OnlineBookstore.strategy.PricingStrategy;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
public class BookstoreService {
    private final Map<String, Book> catalog = new LinkedHashMap<>();
    private final Map<String, List<CartItem>> carts = new HashMap<>();
    private final Map<String, Order> orders = new LinkedHashMap<>();
    private final List<OrderObserver> observers = new CopyOnWriteArrayList<>();
    private volatile PricingStrategy pricing;
    public BookstoreService(PricingStrategy pricing) { this.pricing = pricing; }
    public void setPricing(PricingStrategy p) { this.pricing = p; System.out.println("[STORE] Pricing: " + p.getName()); }
    public void addObserver(OrderObserver o) { observers.add(o); }
    public void addBook(Book b) { catalog.put(b.getIsbn(), b); }
    public void addToCart(String userId, String isbn, int qty) {
        Book book = catalog.get(isbn);
        if (book == null) { System.out.println("[STORE] ❌ Book not found"); return; }
        List<CartItem> cart = carts.computeIfAbsent(userId, k -> new ArrayList<>());
        cart.stream().filter(ci -> ci.getBook().getIsbn().equals(isbn)).findFirst().ifPresentOrElse(ci -> ci.addQuantity(qty), () -> cart.add(new CartItem(book, qty)));
        System.out.println("[STORE] 🛒 " + userId + " added " + book.getTitle() + " x" + qty);
    }
    public Optional<Order> checkout(String userId) {
        List<CartItem> cart = carts.get(userId);
        if (cart == null || cart.isEmpty()) { System.out.println("[STORE] ❌ Empty cart"); return Optional.empty(); }
        for (CartItem ci : cart) {
            if (!ci.getBook().decrementStock(ci.getQuantity())) {
                System.out.println("[STORE] ❌ Insufficient stock: " + ci.getBook().getTitle()); cart.forEach(c -> c.getBook().incrementStock(c.getQuantity())); return Optional.empty();
            }
        }
        double total = cart.stream().mapToDouble(ci -> pricing.calculatePrice(ci.getBook().getBasePrice()) * ci.getQuantity()).sum();
        Order order = new Order(userId, cart, total);
        orders.put(order.getOrderId(), order); carts.remove(userId);
        observers.forEach(o -> o.onOrderPlaced(order));
        System.out.println("[STORE] ✅ " + order); return Optional.of(order);
    }
    public void shipOrder(String orderId) {
        Order o = orders.get(orderId);
        if (o != null) { o.setStatus(Order.Status.SHIPPED); observers.forEach(ob -> ob.onOrderShipped(o)); System.out.println("[STORE] 🚚 " + o); }
    }
    public void showCatalog() { System.out.println("Catalog:"); catalog.values().forEach(b -> System.out.println("  " + b)); }
}
