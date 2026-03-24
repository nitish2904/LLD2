package LibraryManagement.service;

import LibraryManagement.model.*;
import LibraryManagement.observer.LibraryObserver;
import LibraryManagement.repository.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class LoanService {
    private final BookRepository bookRepo;
    private final MemberRepository memberRepo;
    private final LoanRepository loanRepo;
    private final List<LibraryObserver> observers;

    public LoanService(BookRepository bookRepo, MemberRepository memberRepo, LoanRepository loanRepo) {
        this.bookRepo = bookRepo;
        this.memberRepo = memberRepo;
        this.loanRepo = loanRepo;
        this.observers = new CopyOnWriteArrayList<>();
    }

    public void addObserver(LibraryObserver observer) { observers.add(observer); }

    public Optional<Loan> borrowBook(String isbn, String memberId) {
        Optional<Book> bookOpt = bookRepo.findByIsbn(isbn);
        Optional<Member> memberOpt = memberRepo.findById(memberId);
        if (bookOpt.isEmpty() || memberOpt.isEmpty()) {
            System.out.println("[LOAN] Book or member not found");
            return Optional.empty();
        }
        Book book = bookOpt.get();
        Member member = memberOpt.get();
        book.lock();
        try {
            if (!member.canBorrow()) {
                System.out.println("[LOAN] " + member.getName() + " has reached max borrow limit");
                return Optional.empty();
            }
            boolean borrowed = book.borrow(member);
            if (!borrowed) {
                System.out.println("[LOAN] ❌ Cannot borrow \"" + book.getTitle() + "\" for " + member.getName());
                return Optional.empty();
            }
            Loan loan = new Loan(book, member);
            loanRepo.add(loan);
            member.addLoan(loan);
            System.out.println("[LOAN] ✅ " + loan);
            return Optional.of(loan);
        } finally {
            book.unlock();
        }
    }

    public Optional<Loan> borrowBookWithDate(String isbn, String memberId, LocalDate borrowDate) {
        Optional<Book> bookOpt = bookRepo.findByIsbn(isbn);
        Optional<Member> memberOpt = memberRepo.findById(memberId);
        if (bookOpt.isEmpty() || memberOpt.isEmpty()) return Optional.empty();
        Book book = bookOpt.get();
        Member member = memberOpt.get();
        book.lock();
        try {
            boolean borrowed = book.borrow(member);
            if (!borrowed) return Optional.empty();
            Loan loan = new Loan(book, member, borrowDate);
            loanRepo.add(loan);
            member.addLoan(loan);
            System.out.println("[LOAN] ✅ " + loan);
            return Optional.of(loan);
        } finally {
            book.unlock();
        }
    }

    public boolean returnBook(String loanId) {
        Optional<Loan> loanOpt = loanRepo.findById(loanId);
        if (loanOpt.isEmpty()) {
            System.out.println("[LOAN] Loan not found: " + loanId);
            return false;
        }
        Loan loan = loanOpt.get();
        Book book = loan.getBook();
        book.lock();
        try {
            boolean returned = book.returnBook();
            if (!returned) return false;
            loan.close();
            loan.getMember().removeLoan(loan);
            for (LibraryObserver obs : observers) obs.onBookReturned(book);
            System.out.println("[LOAN] 📗 Returned: " + loan);
            return true;
        } finally {
            book.unlock();
        }
    }

    public boolean reserveBook(String isbn, String memberId) {
        Optional<Book> bookOpt = bookRepo.findByIsbn(isbn);
        Optional<Member> memberOpt = memberRepo.findById(memberId);
        if (bookOpt.isEmpty() || memberOpt.isEmpty()) return false;
        Book book = bookOpt.get();
        Member member = memberOpt.get();
        book.lock();
        try {
            boolean reserved = book.reserve(member);
            if (reserved) {
                for (LibraryObserver obs : observers) obs.onBookReserved(book, member);
            }
            return reserved;
        } finally {
            book.unlock();
        }
    }

    public List<Loan> getActiveLoans(String memberId) { return loanRepo.findByMember(memberId); }
    public List<Loan> getOverdueLoans() { return loanRepo.findOverdue(); }
    public LoanRepository getLoanRepo() { return loanRepo; }
}
