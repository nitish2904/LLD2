package LibraryManagement.state;

import LibraryManagement.model.Book;
import LibraryManagement.model.BookStatus;
import LibraryManagement.model.Member;

public interface BookState {
    boolean borrow(Book book, Member member);
    boolean returnBook(Book book);
    boolean reserve(Book book, Member member);
    BookStatus getStatus();
}
