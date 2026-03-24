package ParkingLot.service;

import ParkingLot.model.ParkingSpot;
import ParkingLot.model.Ticket;
import ParkingLot.model.Vehicle;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages ticket lifecycle — issue and close.
 * Thread-safe: uses ConcurrentHashMap for concurrent entry/exit.
 * Single Responsibility: only handles ticket operations.
 */
public class TicketService {
    private final ConcurrentHashMap<String, Ticket> activeTickets;

    public TicketService() {
        this.activeTickets = new ConcurrentHashMap<>();
    }

    public Ticket issueTicket(Vehicle vehicle, ParkingSpot spot) {
        Ticket ticket = new Ticket(vehicle, spot);
        activeTickets.put(ticket.getTicketId(), ticket);
        System.out.println("[TICKET] Issued: " + ticket);
        return ticket;
    }

    public void closeTicket(Ticket ticket) {
        ticket.close();
        activeTickets.remove(ticket.getTicketId());
        System.out.println("[TICKET] Closed: " + ticket);
    }

    public Optional<Ticket> getTicket(String ticketId) {
        return Optional.ofNullable(activeTickets.get(ticketId));
    }

    public int getActiveTicketCount() {
        return activeTickets.size();
    }
}
