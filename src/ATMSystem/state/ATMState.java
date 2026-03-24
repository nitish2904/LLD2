package ATMSystem.state;
import ATMSystem.model.Card;
public interface ATMState {
    void insertCard(ATMContext ctx, Card card);
    void enterPin(ATMContext ctx, String pin);
    void withdraw(ATMContext ctx, double amount);
    void deposit(ATMContext ctx, double amount);
    void checkBalance(ATMContext ctx);
    void ejectCard(ATMContext ctx);
    String getStateName();
}
