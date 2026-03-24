package LibraryManagement.service;

import LibraryManagement.model.Fine;
import LibraryManagement.model.Loan;
import LibraryManagement.repository.LoanRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FineService {
    private final double dailyFineRate;
    private final LoanRepository loanRepo;

    public FineService(double dailyFineRate, LoanRepository loanRepo) {
        this.dailyFineRate = dailyFineRate;
        this.loanRepo = loanRepo;
    }

    public Optional<Fine> calculateFine(Loan loan) {
        if (!loan.isOverdue()) return Optional.empty();
        double amount = loan.getOverdueDays() * dailyFineRate;
        Fine fine = new Fine(loan, amount);
        System.out.println("[FINE] " + fine + " for \"" + loan.getBook().getTitle() + "\" (" + loan.getOverdueDays() + " days overdue)");
        return Optional.of(fine);
    }

    public List<Fine> calculateAllFines() {
        return loanRepo.findOverdue().stream()
                .map(this::calculateFine)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
