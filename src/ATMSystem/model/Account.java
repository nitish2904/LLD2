package ATMSystem.model;
public class Account {
    private final String accountId;
    private final String holderName;
    private double balance;
    public Account(String accountId, String holderName, double balance) {
        this.accountId = accountId; this.holderName = holderName; this.balance = balance;
    }
    public synchronized boolean withdraw(double amount) {
        if (amount > balance) return false;
        balance -= amount; return true;
    }
    public synchronized void deposit(double amount) { balance += amount; }
    public String getAccountId() { return accountId; }
    public String getHolderName() { return holderName; }
    public double getBalance() { return balance; }
    @Override public String toString() { return "Account[" + accountId + " | " + holderName + " | $" + String.format("%.2f", balance) + "]"; }
}
