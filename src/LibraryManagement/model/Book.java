package LibraryManagement.model;

import LibraryManagement.state.AvailableState;
import LibraryManagement.state.BookState;
import java.util.concurrent.locks.ReentrantLock;

public class Book {
    private final String isbn;
    private final String title;
    private final String author;
    private final String subject;
    private BookState state;
    private final ReentrantLock lock;

    public Book(String isbn, String title, String author, String subject) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.subject = subject;
        this.state = new AvailableState();
        this.lock = new ReentrantLock();
    }

    public void lock() { lock.lock(); }
    public void unlock() { lock.unlock(); }

    public boolean borrow(Member member) { return state.borrow(this, member); }
    public boolean returnBook() { return state.returnBook(this); }
    public boolean reserve(Member member) { return state.reserve(this, member); }
    public boolean isAvailable() { return state.getStatus() == BookStatus.AVAILABLE; }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getSubject() { return subject; }
    public BookState getState() { return state; }
    public void setState(BookState state) { this.state = state; }
    public BookStatus getStatus() { return state.getStatus(); }

    @Override
    public String toString() {
        return "Book[" + isbn + " | \"" + title + "\" by " + author + " | " + state.getStatus() + "]";
    }
}
