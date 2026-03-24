package ATMSystem.state;
import ATMSystem.model.Card;
public class IdleState implements ATMState {
    public void insertCard(ATMContext ctx, Card card) { ctx.setCurrentCard(card); ctx.setState(new CardInsertedState()); System.out.println("[ATM] Card inserted: " + card); }
    public void enterPin(ATMContext ctx, String pin) { System.out.println("[ATM] Please insert card first"); }
    public void withdraw(ATMContext ctx, double amount) { System.out.println("[ATM] Please insert card first"); }
    public void deposit(ATMContext ctx, double amount) { System.out.println("[ATM] Please insert card first"); }
    public void checkBalance(ATMContext ctx) { System.out.println("[ATM] Please insert card first"); }
    public void ejectCard(ATMContext ctx) { System.out.println("[ATM] No card inserted"); }
    public String getStateName() { return "IDLE"; }
}
