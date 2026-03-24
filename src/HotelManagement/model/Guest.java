package HotelManagement.model;
public class Guest {
    private final String guestId, name, email, phone;
    public Guest(String guestId, String name, String email, String phone) { this.guestId = guestId; this.name = name; this.email = email; this.phone = phone; }
    public String getGuestId() { return guestId; }
    public String getName() { return name; }
    @Override public String toString() { return name + "(" + guestId + ")"; }
}
