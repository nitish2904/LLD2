package OnlineBookstore.strategy;
public class FlatPricingStrategy implements PricingStrategy {
    public double calculatePrice(double basePrice) { return basePrice; }
    public String getName() { return "Flat"; }
}
