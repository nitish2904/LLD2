package VendingMachine.state;
import VendingMachine.model.Product;
public class DispensingState implements VendingState {
    public void insertCoin(VendingContext ctx, double amount) { System.out.println("[VM] Please wait, dispensing..."); }
    public void selectProduct(VendingContext ctx, String code) { System.out.println("[VM] Please wait, dispensing..."); }
    public void dispense(VendingContext ctx) {
        Product p = ctx.getProduct(ctx.getSelectedCode());
        if (p.dispense()) {
            double change = ctx.getBalance() - p.getPrice();
            System.out.println("[VM] ✅ Dispensed: " + p.getName());
            if (change > 0.001) System.out.println("[VM] 💰 Change: $" + String.format("%.2f", change));
        } else { System.out.println("[VM] ❌ Dispense failed"); }
        ctx.resetBalance(); ctx.setSelectedCode(null); ctx.setState(new IdleState());
    }
    public void cancelTransaction(VendingContext ctx) { System.out.println("[VM] Cannot cancel during dispensing"); }
    public String getName() { return "DISPENSING"; }
}
