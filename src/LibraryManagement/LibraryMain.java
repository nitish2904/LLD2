package LibraryManagement;

import LibraryManagement.model.*;
import LibraryManagement.observer.MemberNotifier;
import LibraryManagement.repository.*;
import LibraryManagement.service.*;
import LibraryManagement.strategy.*;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;

public class LibraryMain {
    public static void main(String[] args) throws Exception {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   📚 LIBRARY MANAGEMENT SYSTEM DEMO   ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        // Repositories
        BookRepository bookRepo = new BookRepository();
        MemberRepository memberRepo = new MemberRepository();
        LoanRepository loanRepo = new LoanRepository();

        // Services
        BookService bookService = new BookService(bookRepo);
        LoanService loanService = new LoanService(bookRepo, memberRepo, loanRepo);
        SearchService searchService = new SearchService(bookRepo, new TitleSearchStrategy());
        FineService fineService = new FineService(1.50, loanRepo);
        loanService.addObserver(new MemberNotifier());

        // === SCENARIO 1: Add Books ===
        System.out.println("═══ SCENARIO 1: Add Books ═══\n");
        bookService.addBook("978-0-13-468599-1", "The Pragmatic Programmer", "David Thomas", "Software");
        bookService.addBook("978-0-20-161622-4", "The Pragmatic Programmer 2", "David Thomas", "Software");
        bookService.addBook("978-0-59-651798-7", "Head First Design Patterns", "Eric Freeman", "Design");
        bookService.addBook("978-0-13-235088-4", "Clean Code", "Robert Martin", "Software");
        bookService.addBook("978-0-20-163361-0", "Design Patterns", "Gang of Four", "Design");

        // === SCENARIO 2: Register Members ===
        System.out.println("\n═══ SCENARIO 2: Register Members ═══\n");
        Member alice = new Member("M001", "Alice", "alice@lib.com");
        Member bob = new Member("M002", "Bob", "bob@lib.com");
        Member charlie = new Member("M003", "Charlie", "charlie@lib.com", 2);
        memberRepo.add(alice);
        memberRepo.add(bob);
        memberRepo.add(charlie);
        System.out.println("Registered: " + alice);
        System.out.println("Registered: " + bob);
        System.out.println("Registered: " + charlie);

        // === SCENARIO 3: Borrow Books ===
        System.out.println("\n═══ SCENARIO 3: Alice Borrows 2 Books ═══\n");
        Optional<Loan> loan1 = loanService.borrowBook("978-0-13-468599-1", "M001");
        Optional<Loan> loan2 = loanService.borrowBook("978-0-13-235088-4", "M001");
        System.out.println("Alice's loans: " + loanService.getActiveLoans("M001").size());

        // === SCENARIO 4: Search ===
        System.out.println("\n═══ SCENARIO 4: Search Books ═══\n");
        List<Book> results = searchService.search("pragmatic");
        results.forEach(b -> System.out.println("  Found: " + b));

        searchService.setStrategy(new AuthorSearchStrategy());
        results = searchService.search("robert");
        results.forEach(b -> System.out.println("  Found: " + b));

        searchService.setStrategy(new IsbnSearchStrategy());
        results = searchService.search("978-0-20-163361-0");
        results.forEach(b -> System.out.println("  Found: " + b));

        // === SCENARIO 5: Bob tries to borrow already-borrowed book ===
        System.out.println("\n═══ SCENARIO 5: Bob Tries to Borrow Borrowed Book ═══\n");
        Optional<Loan> failedLoan = loanService.borrowBook("978-0-13-468599-1", "M002");
        System.out.println("Bob borrow result: " + (failedLoan.isPresent() ? "SUCCESS" : "FAILED (expected)"));

        // === SCENARIO 6: Bob reserves the book ===
        System.out.println("\n═══ SCENARIO 6: Bob Reserves the Borrowed Book ═══\n");
        boolean reserved = loanService.reserveBook("978-0-13-468599-1", "M002");
        System.out.println("Reservation: " + (reserved ? "SUCCESS" : "FAILED"));

        // === SCENARIO 7: Alice returns → Observer notifies ===
        System.out.println("\n═══ SCENARIO 7: Alice Returns Book → Bob Gets Notified ═══\n");
        if (loan1.isPresent()) {
            loanService.returnBook(loan1.get().getLoanId());
        }

        // === SCENARIO 8: Bob borrows the reserved book ===
        System.out.println("\n═══ SCENARIO 8: Bob Borrows Reserved Book ═══\n");
        Optional<Loan> bobLoan = loanService.borrowBook("978-0-13-468599-1", "M002");
        System.out.println("Bob borrow reserved: " + (bobLoan.isPresent() ? "SUCCESS ✅" : "FAILED"));

        // Charlie tries (should fail - reserved for Bob)
        Optional<Loan> charlieFail = loanService.borrowBook("978-0-13-468599-1", "M003");
        System.out.println("Charlie borrow reserved: " + (charlieFail.isPresent() ? "SUCCESS" : "FAILED (expected) ✅"));

        // === SCENARIO 9: Overdue Fine ===
        System.out.println("\n═══ SCENARIO 9: Overdue Fine Calculation ═══\n");
        Optional<Loan> overdueLoan = loanService.borrowBookWithDate("978-0-59-651798-7", "M003",
                LocalDate.now().minusDays(30));
        if (overdueLoan.isPresent()) {
            System.out.println("Overdue? " + overdueLoan.get().isOverdue() + " (" + overdueLoan.get().getOverdueDays() + " days)");
            fineService.calculateFine(overdueLoan.get());
        }

        // === SCENARIO 10: Concurrent Borrow ===
        System.out.println("\n═══ SCENARIO 10: Concurrent Borrow (5 threads, 1 book) ═══\n");
        String targetIsbn = "978-0-20-163361-0";
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Future<Optional<Loan>>> futures = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            final String mid = "M00" + (i + 1);
            // Ensure members exist
            if (memberRepo.findById(mid).isEmpty()) {
                memberRepo.add(new Member(mid, "User" + i, "u" + i + "@lib.com"));
            }
            futures.add(executor.submit(() -> loanService.borrowBook(targetIsbn, mid)));
        }
        int successes = 0, failures = 0;
        for (Future<Optional<Loan>> f : futures) {
            if (f.get().isPresent()) successes++;
            else failures++;
        }
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("\nConcurrent result: " + successes + " succeeded, " + failures + " failed (expected: 1 succeeded, 4 failed)");

        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║      ✅ ALL SCENARIOS COMPLETE          ║");
        System.out.println("╚════════════════════════════════════════╝");
    }
}
