package LibraryManagement.repository;

import LibraryManagement.model.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class BookRepository {
    private final ConcurrentHashMap<String, Book> books = new ConcurrentHashMap<>();

    public void add(Book book) { books.put(book.getIsbn(), book); }
    public Optional<Book> findByIsbn(String isbn) { return Optional.ofNullable(books.get(isbn)); }
    public List<Book> findAll() { return new ArrayList<>(books.values()); }
    public void remove(String isbn) { books.remove(isbn); }
    public int size() { return books.size(); }
}
