package LibraryManagement.strategy;

import LibraryManagement.model.Book;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorSearchStrategy implements SearchStrategy {
    @Override
    public List<Book> search(Collection<Book> books, String query) {
        return books.stream()
                .filter(b -> b.getAuthor().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}
