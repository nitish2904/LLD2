package LibraryManagement.service;

import LibraryManagement.model.Book;
import LibraryManagement.repository.BookRepository;
import LibraryManagement.strategy.SearchStrategy;
import java.util.List;

public class SearchService {
    private final BookRepository bookRepo;
    private volatile SearchStrategy strategy;

    public SearchService(BookRepository bookRepo, SearchStrategy strategy) {
        this.bookRepo = bookRepo;
        this.strategy = strategy;
    }

    public List<Book> search(String query) {
        List<Book> results = strategy.search(bookRepo.findAll(), query);
        System.out.println("[SEARCH] \"" + query + "\" using " + strategy.getClass().getSimpleName() + " → " + results.size() + " result(s)");
        return results;
    }

    public void setStrategy(SearchStrategy strategy) {
        this.strategy = strategy;
        System.out.println("[SEARCH] Strategy changed to: " + strategy.getClass().getSimpleName());
    }
}
