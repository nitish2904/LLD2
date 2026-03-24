package OnlineBookstore.strategy;
public class MemberDiscountStrategy implements PricingStrategy {
    private final double discountPct;
    public MemberDiscountStrategy(double discountPct) { this.discountPct = discountPct; }
    public double calculatePrice(double basePrice) { return basePrice * (1 - discountPct / 100); }
    public String getName() { return "MemberDiscount(" + discountPct + "%)"; }
}
