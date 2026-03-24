package ATMSystem.state;
import ATMSystem.model.Card;
public class CardInsertedState implements ATMState {
    private int attempts = 0;
    public void insertCard(ATMContext ctx, Card card) { System.out.println("[ATM] Card already inserted"); }
    public void enterPin(ATMContext ctx, String pin) {
        if (ctx.getCurrentCard().validatePin(pin)) { ctx.setState(new AuthenticatedState()); System.out.println("[ATM] ✅ PIN verified"); }
        else { attempts++; if (attempts >= 3) { System.out.println("[ATM] ❌ Too many attempts. Ejecting."); ctx.ejectCard(); } else System.out.println("[ATM] ❌ Wrong PIN (" + (3-attempts) + " left)"); }
    }
    public void withdraw(ATMContext ctx, double amount) { System.out.println("[ATM] Enter PIN first"); }
    public void deposit(ATMContext ctx, double amount) { System.out.println("[ATM] Enter PIN first"); }
    public void checkBalance(ATMContext ctx) { System.out.println("[ATM] Enter PIN first"); }
    public void ejectCard(ATMContext ctx) { ctx.clearSession(); ctx.setState(new IdleState()); System.out.println("[ATM] Card ejected"); }
    public String getStateName() { return "CARD_INSERTED"; }
}
