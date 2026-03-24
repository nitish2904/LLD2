package LibraryManagement.model;

import java.util.UUID;

public class Fine {
    private final String fineId;
    private final Loan loan;
    private final double amount;
    private boolean paid;

    public Fine(Loan loan, double amount) {
        this.fineId = "FINE-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        this.loan = loan;
        this.amount = amount;
        this.paid = false;
    }

    public void pay() { this.paid = true; }
    public String getFineId() { return fineId; }
    public Loan getLoan() { return loan; }
    public double getAmount() { return amount; }
    public boolean isPaid() { return paid; }

    @Override
    public String toString() {
        return "Fine[" + fineId + " | $" + String.format("%.2f", amount) + " | " + (paid ? "PAID" : "UNPAID") + "]";
    }
}
