package VendingMachine.state;
public class IdleState implements VendingState {
    public void insertCoin(VendingContext ctx, double amount) { ctx.addBalance(amount); ctx.setState(new HasMoneyState()); System.out.println("[VM] Inserted $" + String.format("%.2f", amount) + " | Balance: $" + String.format("%.2f", ctx.getBalance())); }
    public void selectProduct(VendingContext ctx, String code) { System.out.println("[VM] Insert coins first"); }
    public void dispense(VendingContext ctx) { System.out.println("[VM] Insert coins first"); }
    public void cancelTransaction(VendingContext ctx) { System.out.println("[VM] Nothing to cancel"); }
    public String getName() { return "IDLE"; }
}
