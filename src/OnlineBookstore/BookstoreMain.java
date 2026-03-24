package OnlineBookstore;
import OnlineBookstore.model.*; import OnlineBookstore.observer.EmailNotifier;
import OnlineBookstore.service.BookstoreService; import OnlineBookstore.strategy.*;
public class BookstoreMain {
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║   📚 ONLINE BOOKSTORE DEMO        ║");
        System.out.println("╚════════════════════════════════════╝\n");
        BookstoreService store = new BookstoreService(new FlatPricingStrategy());
        store.addObserver(new EmailNotifier());
        store.addBook(new Book("978-1", "Clean Code", "Robert Martin", 39.99, 10));
        store.addBook(new Book("978-2", "Design Patterns", "GoF", 49.99, 5));
        store.addBook(new Book("978-3", "DDIA", "Martin Kleppmann", 44.99, 3));
        store.showCatalog();

        System.out.println("\n═══ SCENARIO 1: Regular checkout ═══\n");
        store.addToCart("alice", "978-1", 1); store.addToCart("alice", "978-2", 2);
        var o1 = store.checkout("alice");

        System.out.println("\n═══ SCENARIO 2: Member discount ═══\n");
        store.setPricing(new MemberDiscountStrategy(20));
        store.addToCart("bob", "978-3", 1); store.addToCart("bob", "978-1", 1);
        var o2 = store.checkout("bob");

        System.out.println("\n═══ SCENARIO 3: Ship order ═══\n");
        if (o1.isPresent()) store.shipOrder(o1.get().getOrderId());

        System.out.println("\n═══ SCENARIO 4: Out of stock ═══\n");
        store.setPricing(new FlatPricingStrategy());
        store.addToCart("charlie", "978-3", 5);
        store.checkout("charlie");
        store.showCatalog();

        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║   ✅ ALL SCENARIOS COMPLETE         ║");
        System.out.println("╚════════════════════════════════════╝");
    }
}
