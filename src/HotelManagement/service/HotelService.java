package HotelManagement.service;
import HotelManagement.model.*;
import HotelManagement.observer.HotelObserver;
import HotelManagement.strategy.RoomSelectionStrategy;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
public class HotelService {
    private final List<Room> rooms = new ArrayList<>();
    private final Map<String, Reservation> reservations = new LinkedHashMap<>();
    private final List<HotelObserver> observers = new CopyOnWriteArrayList<>();
    private volatile RoomSelectionStrategy strategy;
    public HotelService(RoomSelectionStrategy strategy) { this.strategy = strategy; }
    public void addRoom(Room room) { rooms.add(room); }
    public void setStrategy(RoomSelectionStrategy s) { this.strategy = s; System.out.println("[HOTEL] Strategy: " + s.getName()); }
    public void addObserver(HotelObserver o) { observers.add(o); }
    public Optional<Reservation> makeReservation(Guest guest, RoomType type, LocalDate in, LocalDate out) {
        Optional<Room> roomOpt = strategy.selectRoom(rooms, type);
        if (roomOpt.isEmpty() || !roomOpt.get().book()) { System.out.println("[HOTEL] ❌ No " + type + " room available"); return Optional.empty(); }
        Reservation res = new Reservation(guest, roomOpt.get(), in, out);
        reservations.put(res.getReservationId(), res);
        observers.forEach(o -> o.onReservation(res));
        System.out.println("[HOTEL] ✅ " + res); return Optional.of(res);
    }
    public void checkIn(String resId) {
        Reservation r = reservations.get(resId);
        if (r != null) { r.checkIn(); observers.forEach(o -> o.onCheckIn(r)); System.out.println("[HOTEL] 🔑 " + r); }
    }
    public void checkOut(String resId) {
        Reservation r = reservations.get(resId);
        if (r != null) { r.checkOut(); observers.forEach(o -> o.onCheckOut(r)); System.out.println("[HOTEL] 👋 " + r); }
    }
    public void cancelReservation(String resId) {
        Reservation r = reservations.get(resId);
        if (r != null) { r.cancel(); observers.forEach(o -> o.onCancellation(r)); System.out.println("[HOTEL] 🚫 " + r); }
    }
    public void printRooms() { rooms.forEach(r -> System.out.println("  " + r)); }
}
