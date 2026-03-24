# 🎬 Movie Ticket Booking System — LLD

Implements **Strategy** (seat selection), **Observer** (booking notifications), **State** (seat status), and **Singleton** (BookingSystem) patterns with thread-safety.

## Design Patterns
| Pattern | Purpose |
|---------|---------|
| **Strategy** | Pluggable seat selection (center-first, front-first, cheapest) |
| **Observer** | Notify on booking confirmation/cancellation |
| **State** | Seat lifecycle: AVAILABLE → HELD → BOOKED → CANCELLED |
| **Singleton** | BookingSystem — single entry point |

## Package Structure
```
MovieTicketBooking/
├── model/ (Movie, Theater, Screen, Show, Seat, SeatType, SeatStatus, Booking, BookingStatus)
├── strategy/ (SeatSelectionStrategy, CenterFirstStrategy)
├── observer/ (BookingObserver, EmailNotifier)
├── service/ (BookingService, ShowService)
└── MovieBookingMain.java
```
