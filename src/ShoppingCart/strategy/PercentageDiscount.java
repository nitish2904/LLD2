package ShoppingCart.strategy;
import ShoppingCart.model.Cart;
public class PercentageDiscount implements DiscountStrategy {
    private final double percent;
    public PercentageDiscount(double percent) { this.percent = percent; }
    public double applyDiscount(Cart cart) { return cart.getTotal() * (1 - percent / 100); }
    public String getDescription() { return percent + "% off"; }
}
