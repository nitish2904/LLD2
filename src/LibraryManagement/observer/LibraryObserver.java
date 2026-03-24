package LibraryManagement.observer;

import LibraryManagement.model.Book;
import LibraryManagement.model.Member;

public interface LibraryObserver {
    void onBookReturned(Book book);
    void onBookReserved(Book book, Member member);
}
