package ATMSystem.state;
import ATMSystem.model.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
public class ATMContext {
    private ATMState state;
    private Card currentCard;
    private Account currentAccount;
    private final CashDispenser dispenser;
    private final Map<String, Account> accounts;
    public ATMContext(CashDispenser dispenser) {
        this.dispenser = dispenser;
        this.accounts = new ConcurrentHashMap<>();
        this.state = new IdleState();
    }
    public void registerAccount(Account account, Card card) { accounts.put(card.getAccountId(), account); }
    public void setState(ATMState state) { System.out.println("[ATM] State: " + this.state.getStateName() + " → " + state.getStateName()); this.state = state; }
    public ATMState getState() { return state; }
    public void setCurrentCard(Card card) { this.currentCard = card; this.currentAccount = accounts.get(card.getAccountId()); }
    public Card getCurrentCard() { return currentCard; }
    public Account getCurrentAccount() { return currentAccount; }
    public CashDispenser getDispenser() { return dispenser; }
    public void clearSession() { currentCard = null; currentAccount = null; }
    public void insertCard(Card card) { state.insertCard(this, card); }
    public void enterPin(String pin) { state.enterPin(this, pin); }
    public void withdraw(double amount) { state.withdraw(this, amount); }
    public void deposit(double amount) { state.deposit(this, amount); }
    public void checkBalance() { state.checkBalance(this); }
    public void ejectCard() { state.ejectCard(this); }
}
