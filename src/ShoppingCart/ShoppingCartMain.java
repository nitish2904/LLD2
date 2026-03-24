package ShoppingCart;
import ShoppingCart.model.*;
import ShoppingCart.observer.*;
import ShoppingCart.service.OrderService;
import ShoppingCart.strategy.*;
import java.util.*;
import java.util.concurrent.*;
public class ShoppingCartMain {
    public static void main(String[] args) throws Exception {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   🛒 SHOPPING CART SYSTEM DEMO        ║");
        System.out.println("╚════════════════════════════════════════╝\n");
        Product laptop = new Product("P1", "MacBook Pro", "Electronics", 2499.99, 5);
        Product phone = new Product("P2", "iPhone 15", "Electronics", 999.99, 10);
        Product book = new Product("P3", "Clean Code", "Books", 39.99, 50);
        Product mouse = new Product("P4", "Magic Mouse", "Electronics", 79.99, 20);
        OrderService orderService = new OrderService();
        orderService.addObserver(new EmailNotifier());
        orderService.addObserver(new InventoryTracker());

        System.out.println("═══ SCENARIO 1: Basic Cart + No Discount ═══\n");
        Cart cart1 = new Cart("user1");
        cart1.addItem(laptop, 1); cart1.addItem(phone, 2); cart1.addItem(book, 1);
        System.out.println(cart1);
        cart1.getItems().forEach(i -> System.out.println("  " + i));
        Optional<Order> o1 = orderService.checkout(cart1);

        System.out.println("\n═══ SCENARIO 2: Percentage Discount (10%) ═══\n");
        orderService.setDiscountStrategy(new PercentageDiscount(10));
        Cart cart2 = new Cart("user2");
        cart2.addItem(phone, 1); cart2.addItem(mouse, 2);
        System.out.println(cart2);
        Optional<Order> o2 = orderService.checkout(cart2);

        System.out.println("\n═══ SCENARIO 3: Buy 2 Get Cheapest Free ═══\n");
        orderService.setDiscountStrategy(new BuyNGetFreeDiscount(2));
        Cart cart3 = new Cart("user3");
        cart3.addItem(phone, 1); cart3.addItem(book, 1); cart3.addItem(mouse, 1);
        System.out.println(cart3);
        Optional<Order> o3 = orderService.checkout(cart3);

        System.out.println("\n═══ SCENARIO 4: Cancel & Ship ═══\n");
        if (o1.isPresent()) orderService.shipOrder(o1.get().getOrderId());
        if (o2.isPresent()) orderService.cancelOrder(o2.get().getOrderId());
        System.out.println("Phone stock after cancel: " + phone.getStockQuantity());

        System.out.println("\n═══ SCENARIO 5: Out of Stock ═══\n");
        Cart cart4 = new Cart("user4");
        cart4.addItem(laptop, 10); // only 4 left
        orderService.setDiscountStrategy(new NoDiscount());
        orderService.checkout(cart4);

        System.out.println("\n═══ SCENARIO 6: Concurrent Checkout ═══\n");
        Product limited = new Product("P5", "Limited Edition", "Special", 199.99, 3);
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Future<Optional<Order>>> futures = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            final String uid = "cuser" + i;
            futures.add(executor.submit(() -> { Cart c = new Cart(uid); c.addItem(limited, 1); return orderService.checkout(c); }));
        }
        int ok = 0, fail = 0;
        for (var f : futures) { if (f.get().isPresent()) ok++; else fail++; }
        executor.shutdown();
        System.out.println("Concurrent: " + ok + " ordered, " + fail + " failed (3 in stock, 5 tried)");

        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║      ✅ ALL SCENARIOS COMPLETE          ║");
        System.out.println("╚════════════════════════════════════════╝");
    }
}
