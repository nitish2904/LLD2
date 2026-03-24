package ShoppingCart.strategy;
import ShoppingCart.model.Cart;
public interface DiscountStrategy {
    double applyDiscount(Cart cart);
    String getDescription();
}
