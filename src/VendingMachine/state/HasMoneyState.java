package VendingMachine.state;
import VendingMachine.model.Product;
public class HasMoneyState implements VendingState {
    public void insertCoin(VendingContext ctx, double amount) { ctx.addBalance(amount); System.out.println("[VM] Inserted $" + String.format("%.2f", amount) + " | Balance: $" + String.format("%.2f", ctx.getBalance())); }
    public void selectProduct(VendingContext ctx, String code) {
        Product p = ctx.getProduct(code);
        if (p == null) { System.out.println("[VM] ❌ Invalid code: " + code); return; }
        if (!p.isAvailable()) { System.out.println("[VM] ❌ " + p.getName() + " is out of stock"); return; }
        if (ctx.getBalance() < p.getPrice()) { System.out.println("[VM] ❌ Insufficient funds. Need $" + String.format("%.2f", p.getPrice()) + ", have $" + String.format("%.2f", ctx.getBalance())); return; }
        ctx.setSelectedCode(code);
        ctx.setState(new DispensingState());
        ctx.dispense();
    }
    public void dispense(VendingContext ctx) { System.out.println("[VM] Select a product first"); }
    public void cancelTransaction(VendingContext ctx) { double refund = ctx.getBalance(); ctx.resetBalance(); ctx.setState(new IdleState()); System.out.println("[VM] 💰 Refunded $" + String.format("%.2f", refund)); }
    public String getName() { return "HAS_MONEY"; }
}
