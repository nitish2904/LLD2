package LibraryManagement.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class Loan {
    private final String loanId;
    private final Book book;
    private final Member member;
    private final LocalDate borrowDate;
    private final LocalDate dueDate;
    private LocalDate returnDate;
    private boolean active;

    public Loan(Book book, Member member) {
        this.loanId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.book = book;
        this.member = member;
        this.borrowDate = LocalDate.now();
        this.dueDate = borrowDate.plusDays(14);
        this.active = true;
    }

    public Loan(Book book, Member member, LocalDate borrowDate) {
        this.loanId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.book = book;
        this.member = member;
        this.borrowDate = borrowDate;
        this.dueDate = borrowDate.plusDays(14);
        this.active = true;
    }

    public boolean isOverdue() { return active && LocalDate.now().isAfter(dueDate); }
    public long getOverdueDays() {
        if (!isOverdue()) return 0;
        LocalDate end = returnDate != null ? returnDate : LocalDate.now();
        return ChronoUnit.DAYS.between(dueDate, end);
    }

    public void close() { this.returnDate = LocalDate.now(); this.active = false; }

    public String getLoanId() { return loanId; }
    public Book getBook() { return book; }
    public Member getMember() { return member; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public boolean isActive() { return active; }

    @Override
    public String toString() {
        return "Loan[" + loanId + " | " + book.getTitle() + " → " + member.getName() +
               " | due=" + dueDate + (active ? " | ACTIVE" : " | RETURNED " + returnDate) +
               (isOverdue() ? " | OVERDUE " + getOverdueDays() + "d" : "") + "]";
    }
}
