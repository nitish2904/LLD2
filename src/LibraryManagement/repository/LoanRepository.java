package LibraryManagement.repository;

import LibraryManagement.model.Loan;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class LoanRepository {
    private final ConcurrentHashMap<String, Loan> loans = new ConcurrentHashMap<>();

    public void add(Loan loan) { loans.put(loan.getLoanId(), loan); }
    public Optional<Loan> findById(String id) { return Optional.ofNullable(loans.get(id)); }
    public List<Loan> findByMember(String memberId) {
        return loans.values().stream().filter(l -> l.getMember().getMemberId().equals(memberId) && l.isActive()).collect(Collectors.toList());
    }
    public Optional<Loan> findActiveByBook(String isbn) {
        return loans.values().stream().filter(l -> l.getBook().getIsbn().equals(isbn) && l.isActive()).findFirst();
    }
    public List<Loan> findOverdue() {
        return loans.values().stream().filter(Loan::isOverdue).collect(Collectors.toList());
    }
    public List<Loan> findAll() { return new ArrayList<>(loans.values()); }
}
