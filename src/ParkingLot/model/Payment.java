package ParkingLot.model;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Payment record generated when a vehicle exits.
 */
public class Payment {
    private final String paymentId;
    private final Ticket ticket;
    private final double amount;
    private final LocalDateTime paymentTime;

    public Payment(Ticket ticket, double amount) {
        this.paymentId = "PAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.ticket = ticket;
        this.amount = amount;
        this.paymentTime = LocalDateTime.now();
    }

    public String getPaymentId() { return paymentId; }
    public Ticket getTicket() { return ticket; }
    public double getAmount() { return amount; }
    public LocalDateTime getPaymentTime() { return paymentTime; }

    @Override
    public String toString() {
        return "Payment[" + paymentId + " | ticket=" + ticket.getTicketId() +
               " | $" + String.format("%.2f", amount) + " | " + paymentTime + "]";
    }
}
