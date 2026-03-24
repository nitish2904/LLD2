package LibraryManagement.observer;

import LibraryManagement.model.Book;
import LibraryManagement.model.Member;

public class MemberNotifier implements LibraryObserver {
    @Override
    public void onBookReturned(Book book) {
        System.out.println("[NOTIFY] 📗 \"" + book.getTitle() + "\" has been returned and is now " + book.getStatus());
    }

    @Override
    public void onBookReserved(Book book, Member member) {
        System.out.println("[NOTIFY] 📕 \"" + book.getTitle() + "\" has been reserved for " + member.getName());
    }
}
