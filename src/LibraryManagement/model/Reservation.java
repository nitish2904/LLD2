package LibraryManagement.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Reservation {
    private final String reservationId;
    private final Book book;
    private final Member member;
    private final LocalDateTime reservedAt;
    private boolean fulfilled;

    public Reservation(Book book, Member member) {
        this.reservationId = "RES-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        this.book = book;
        this.member = member;
        this.reservedAt = LocalDateTime.now();
        this.fulfilled = false;
    }

    public void fulfill() { this.fulfilled = true; }
    public String getReservationId() { return reservationId; }
    public Book getBook() { return book; }
    public Member getMember() { return member; }
    public LocalDateTime getReservedAt() { return reservedAt; }
    public boolean isFulfilled() { return fulfilled; }

    @Override
    public String toString() {
        return "Reservation[" + reservationId + " | " + book.getTitle() + " for " + member.getName() + " | " + (fulfilled ? "FULFILLED" : "PENDING") + "]";
    }
}
