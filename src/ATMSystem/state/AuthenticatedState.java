package ATMSystem.state;
import ATMSystem.model.*;
import java.util.Map;
public class AuthenticatedState implements ATMState {
    public void insertCard(ATMContext ctx, Card card) { System.out.println("[ATM] Session active"); }
    public void enterPin(ATMContext ctx, String pin) { System.out.println("[ATM] Already authenticated"); }
    public void withdraw(ATMContext ctx, double amount) {
        Account acc = ctx.getCurrentAccount();
        if (!acc.withdraw(amount)) { System.out.println("[ATM] ❌ Insufficient balance ($" + String.format("%.2f", acc.getBalance()) + ")"); return; }
        Map<Integer, Integer> cash = ctx.getDispenser().dispense(amount);
        if (cash == null) { acc.deposit(amount); System.out.println("[ATM] ❌ ATM cannot dispense $" + amount); return; }
        System.out.println("[ATM] ✅ Dispensed $" + String.format("%.2f", amount) + ": " + cash);
        System.out.println("[ATM] New balance: $" + String.format("%.2f", acc.getBalance()));
    }
    public void deposit(ATMContext ctx, double amount) {
        ctx.getCurrentAccount().deposit(amount);
        System.out.println("[ATM] ✅ Deposited $" + String.format("%.2f", amount) + " | Balance: $" + String.format("%.2f", ctx.getCurrentAccount().getBalance()));
    }
    public void checkBalance(ATMContext ctx) { System.out.println("[ATM] 💰 Balance: $" + String.format("%.2f", ctx.getCurrentAccount().getBalance())); }
    public void ejectCard(ATMContext ctx) { ctx.clearSession(); ctx.setState(new IdleState()); System.out.println("[ATM] Card ejected. Thank you!"); }
    public String getStateName() { return "AUTHENTICATED"; }
}
