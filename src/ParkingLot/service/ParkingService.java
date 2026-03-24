package ParkingLot.service;

import ParkingLot.model.*;
import ParkingLot.observer.ParkingLotObserver;
import ParkingLot.strategy.ParkingStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Core parking service — orchestrates park and unpark operations.
 * 
 * Dependency Inversion: depends on ParkingStrategy interface, not concrete strategies.
 * Observer Pattern: notifies all registered observers on spot changes.
 * Thread-safe: delegates locking to ParkingFloor (per-floor ReentrantLock).
 */
public class ParkingService {
    private volatile ParkingStrategy strategy;
    private final TicketService ticketService;
    private final PaymentService paymentService;
    private final List<ParkingLotObserver> observers;

    public ParkingService(ParkingStrategy strategy, TicketService ticketService, PaymentService paymentService) {
        this.strategy = strategy;
        this.ticketService = ticketService;
        this.paymentService = paymentService;
        this.observers = new CopyOnWriteArrayList<>();
    }

    /**
     * Park a vehicle in the parking lot.
     * 1. Use strategy to find a spot
     * 2. Lock the floor and park
     * 3. Issue ticket
     * 4. Notify observers
     */
    public Optional<Ticket> parkVehicle(ParkingLot parkingLot, Vehicle vehicle) {
        List<ParkingFloor> floors = parkingLot.getFloors();

        // Strategy finds best spot (read-only scan)
        Optional<ParkingSpot> spotOpt = strategy.findSpot(floors, vehicle);
        if (spotOpt.isEmpty()) {
            System.out.println("[PARKING] ❌ No spot available for " + vehicle);
            return Optional.empty();
        }

        ParkingSpot spot = spotOpt.get();

        // Find the floor this spot belongs to
        ParkingFloor floor = floors.stream()
                .filter(f -> f.getFloorNumber() == spot.getFloorNumber())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Floor not found for spot: " + spot));

        // Park under floor lock (thread-safe)
        boolean parked = floor.parkVehicle(spot, vehicle);
        if (!parked) {
            // Spot was taken between strategy scan and lock acquisition — retry
            System.out.println("[PARKING] ⚠️ Spot " + spot.getSpotId() + " was taken, retrying...");
            return parkVehicle(parkingLot, vehicle); // Recursive retry
        }

        // Issue ticket
        Ticket ticket = ticketService.issueTicket(vehicle, spot);

        // Notify observers
        notifySpotTaken(floor, spot);

        System.out.println("[PARKING] ✅ Parked " + vehicle + " at " + spot.getSpotId() +
                " on Floor-" + floor.getFloorNumber());
        return Optional.of(ticket);
    }

    /**
     * Unpark a vehicle — close ticket, calculate payment, free spot, notify observers.
     */
    public Payment unparkVehicle(ParkingLot parkingLot, Ticket ticket) {
        if (!ticket.isActive()) {
            throw new IllegalStateException("Ticket already closed: " + ticket.getTicketId());
        }

        ParkingSpot spot = ticket.getSpot();
        ParkingFloor floor = parkingLot.getFloors().stream()
                .filter(f -> f.getFloorNumber() == spot.getFloorNumber())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Floor not found for spot: " + spot));

        // Unpark under floor lock
        Vehicle vehicle = floor.unparkVehicle(spot);

        // Close ticket and process payment
        ticketService.closeTicket(ticket);
        Payment payment = paymentService.processPayment(ticket);

        // Notify observers
        notifySpotFreed(floor, spot);

        System.out.println("[PARKING] 🚗 Unparked " + vehicle + " from " + spot.getSpotId() +
                " | " + payment);
        return payment;
    }

    public void setStrategy(ParkingStrategy strategy) {
        this.strategy = strategy;
        System.out.println("[PARKING] Strategy changed to: " + strategy.getClass().getSimpleName());
    }

    public void addObserver(ParkingLotObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ParkingLotObserver observer) {
        observers.remove(observer);
    }

    private void notifySpotTaken(ParkingFloor floor, ParkingSpot spot) {
        for (ParkingLotObserver observer : observers) {
            observer.onSpotTaken(floor, spot);
        }
    }

    private void notifySpotFreed(ParkingFloor floor, ParkingSpot spot) {
        for (ParkingLotObserver observer : observers) {
            observer.onSpotFreed(floor, spot);
        }
    }

    public TicketService getTicketService() {
        return ticketService;
    }
}
