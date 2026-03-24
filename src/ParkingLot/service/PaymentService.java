package ParkingLot.service;

import ParkingLot.model.Payment;
import ParkingLot.model.Ticket;

import java.time.Duration;

/**
 * Calculates parking fees and processes payments.
 * Single Responsibility: only handles payment logic.
 */
public class PaymentService {
    private final double hourlyRate;

    public PaymentService(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    /**
     * Calculate fee based on duration parked.
     * Minimum charge = 1 hour.
     */
    public double calculateFee(Ticket ticket) {
        if (ticket.getExitTime() == null) {
            throw new IllegalStateException("Ticket not closed yet: " + ticket.getTicketId());
        }
        long minutes = Duration.between(ticket.getEntryTime(), ticket.getExitTime()).toMinutes();
        long hours = Math.max(1, (long) Math.ceil(minutes / 60.0));
        return hours * hourlyRate;
    }

    public Payment processPayment(Ticket ticket) {
        double fee = calculateFee(ticket);
        Payment payment = new Payment(ticket, fee);
        System.out.println("[PAYMENT] Processed: " + payment);
        return payment;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }
}
