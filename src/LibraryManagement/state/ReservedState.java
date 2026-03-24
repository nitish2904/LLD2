package LibraryManagement.state;

import LibraryManagement.model.Book;
import LibraryManagement.model.BookStatus;
import LibraryManagement.model.Member;

public class ReservedState implements BookState {
    private final Member reservedFor;

    public ReservedState(Member reservedFor) {
        this.reservedFor = reservedFor;
    }

    @Override
    public boolean borrow(Book book, Member member) {
        if (member.getMemberId().equals(reservedFor.getMemberId())) {
            book.setState(new BorrowedState());
            System.out.println("[STATE] " + book.getTitle() + ": RESERVED → BORROWED by " + member.getName() + " (reservation fulfilled)");
            return true;
        }
        System.out.println("[STATE] " + book.getTitle() + ": RESERVED for " + reservedFor.getName() + ", " + member.getName() + " cannot borrow");
        return false;
    }

    @Override
    public boolean returnBook(Book book) {
        System.out.println("[STATE] " + book.getTitle() + ": RESERVED, returning to available for " + reservedFor.getName());
        return true;
    }

    @Override
    public boolean reserve(Book book, Member member) {
        System.out.println("[STATE] " + book.getTitle() + ": Already RESERVED for " + reservedFor.getName());
        return false;
    }

    @Override
    public BookStatus getStatus() { return BookStatus.RESERVED; }

    public Member getReservedFor() { return reservedFor; }
}
