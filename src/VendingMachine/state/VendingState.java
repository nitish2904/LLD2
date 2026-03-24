package VendingMachine.state;
public interface VendingState {
    void insertCoin(VendingContext ctx, double amount);
    void selectProduct(VendingContext ctx, String code);
    void dispense(VendingContext ctx);
    void cancelTransaction(VendingContext ctx);
    String getName();
}
