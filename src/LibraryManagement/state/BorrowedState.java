package LibraryManagement.state;

import LibraryManagement.model.Book;
import LibraryManagement.model.BookStatus;
import LibraryManagement.model.Member;

public class BorrowedState implements BookState {
    @Override
    public boolean borrow(Book book, Member member) {
        System.out.println("[STATE] " + book.getTitle() + ": Already BORROWED, cannot borrow again");
        return false;
    }

    @Override
    public boolean returnBook(Book book) {
        book.setState(new AvailableState());
        System.out.println("[STATE] " + book.getTitle() + ": BORROWED → AVAILABLE");
        return true;
    }

    @Override
    public boolean reserve(Book book, Member member) {
        book.setState(new ReservedState(member));
        System.out.println("[STATE] " + book.getTitle() + ": BORROWED → RESERVED for " + member.getName() + " (will be available after return)");
        return true;
    }

    @Override
    public BookStatus getStatus() { return BookStatus.BORROWED; }
}
