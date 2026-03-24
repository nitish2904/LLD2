package ShoppingCart.strategy;
import ShoppingCart.model.Cart;
import ShoppingCart.model.CartItem;
import java.util.Comparator;
public class BuyNGetFreeDiscount implements DiscountStrategy {
    private final int buyN;
    public BuyNGetFreeDiscount(int buyN) { this.buyN = buyN; }
    public double applyDiscount(Cart cart) {
        double total = cart.getTotal();
        long totalItems = cart.getItems().stream().mapToInt(CartItem::getQuantity).sum();
        if (totalItems > buyN) {
            double cheapest = cart.getItems().stream().map(i -> i.getProduct().getPrice()).min(Comparator.naturalOrder()).orElse(0.0);
            total -= cheapest;
        }
        return total;
    }
    public String getDescription() { return "Buy " + buyN + " get cheapest free"; }
}
