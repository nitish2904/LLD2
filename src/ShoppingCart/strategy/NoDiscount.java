package ShoppingCart.strategy;
import ShoppingCart.model.Cart;
public class NoDiscount implements DiscountStrategy {
    public double applyDiscount(Cart cart) { return cart.getTotal(); }
    public String getDescription() { return "No discount"; }
}
