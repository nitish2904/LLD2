package LibraryManagement.state;

import LibraryManagement.model.Book;
import LibraryManagement.model.BookStatus;
import LibraryManagement.model.Member;

public class AvailableState implements BookState {
    @Override
    public boolean borrow(Book book, Member member) {
        book.setState(new BorrowedState());
        System.out.println("[STATE] " + book.getTitle() + ": AVAILABLE → BORROWED by " + member.getName());
        return true;
    }

    @Override
    public boolean returnBook(Book book) {
        System.out.println("[STATE] " + book.getTitle() + ": Already AVAILABLE, cannot return");
        return false;
    }

    @Override
    public boolean reserve(Book book, Member member) {
        book.setState(new ReservedState(member));
        System.out.println("[STATE] " + book.getTitle() + ": AVAILABLE → RESERVED for " + member.getName());
        return true;
    }

    @Override
    public BookStatus getStatus() { return BookStatus.AVAILABLE; }
}
