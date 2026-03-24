package VendingMachine.state;
import VendingMachine.model.Product;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
public class VendingContext {
    private VendingState state;
    private double currentBalance;
    private String selectedCode;
    private final Map<String, Product> inventory = new ConcurrentHashMap<>();
    public VendingContext() { this.state = new IdleState(); this.currentBalance = 0; }
    public void addProduct(Product p) { inventory.put(p.getCode(), p); }
    public Product getProduct(String code) { return inventory.get(code); }
    public void setState(VendingState s) { System.out.println("[VM] " + state.getName() + " → " + s.getName()); state = s; }
    public double getBalance() { return currentBalance; }
    public void addBalance(double amt) { currentBalance += amt; }
    public void resetBalance() { currentBalance = 0; }
    public String getSelectedCode() { return selectedCode; }
    public void setSelectedCode(String code) { selectedCode = code; }
    public void insertCoin(double amount) { state.insertCoin(this, amount); }
    public void selectProduct(String code) { state.selectProduct(this, code); }
    public void dispense() { state.dispense(this); }
    public void cancel() { state.cancelTransaction(this); }
    public void printInventory() { System.out.println("Inventory: " + inventory.values()); }
}
