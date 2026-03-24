package ATMSystem.model;
import java.time.LocalDateTime;
import java.util.UUID;
public class Transaction {
    public enum Type { WITHDRAWAL, DEPOSIT, BALANCE_INQUIRY }
    private final String transactionId;
    private final String accountId;
    private final Type type;
    private final double amount;
    private final LocalDateTime timestamp;
    private final boolean success;
    public Transaction(String accountId, Type type, double amount, boolean success) {
        this.transactionId = "TXN-" + UUID.randomUUID().toString().substring(0,8).toUpperCase();
        this.accountId = accountId; this.type = type; this.amount = amount;
        this.timestamp = LocalDateTime.now(); this.success = success;
    }
    public String getTransactionId() { return transactionId; }
    public Type getType() { return type; }
    public double getAmount() { return amount; }
    public boolean isSuccess() { return success; }
    @Override public String toString() { return "Txn[" + transactionId + " | " + type + " | $" + String.format("%.2f", amount) + " | " + (success ? "✅" : "❌") + "]"; }
}
