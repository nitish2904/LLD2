package LibraryManagement.service;

import LibraryManagement.model.Book;
import LibraryManagement.repository.BookRepository;
import java.util.List;
import java.util.Optional;

public class BookService {
    private final BookRepository bookRepo;

    public BookService(BookRepository bookRepo) { this.bookRepo = bookRepo; }

    public Book addBook(String isbn, String title, String author, String subject) {
        Book book = new Book(isbn, title, author, subject);
        bookRepo.add(book);
        System.out.println("[BOOK] Added: " + book);
        return book;
    }

    public void removeBook(String isbn) { bookRepo.remove(isbn); }
    public Optional<Book> getBook(String isbn) { return bookRepo.findByIsbn(isbn); }
    public List<Book> getAllBooks() { return bookRepo.findAll(); }
}
