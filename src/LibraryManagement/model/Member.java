package LibraryManagement.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Member {
    private final String memberId;
    private final String name;
    private final String email;
    private final List<Loan> activeLoans;
    private final int maxBooks;

    public Member(String memberId, String name, String email, int maxBooks) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.maxBooks = maxBooks;
        this.activeLoans = new ArrayList<>();
    }

    public Member(String memberId, String name, String email) {
        this(memberId, name, email, 5);
    }

    public boolean canBorrow() { return activeLoans.size() < maxBooks; }
    public void addLoan(Loan loan) { activeLoans.add(loan); }
    public void removeLoan(Loan loan) { activeLoans.remove(loan); }

    public String getMemberId() { return memberId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public List<Loan> getActiveLoans() { return Collections.unmodifiableList(activeLoans); }
    public int getMaxBooks() { return maxBooks; }

    @Override
    public String toString() {
        return "Member[" + memberId + " | " + name + " | loans=" + activeLoans.size() + "/" + maxBooks + "]";
    }
}
