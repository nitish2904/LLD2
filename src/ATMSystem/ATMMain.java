package ATMSystem;
import ATMSystem.model.*;
import ATMSystem.state.ATMContext;
public class ATMMain {
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════╗");
        System.out.println("║    🏧 ATM SYSTEM DEMO         ║");
        System.out.println("╚════════════════════════════════╝\n");

        CashDispenser dispenser = new CashDispenser();
        dispenser.loadCash(100, 10); dispenser.loadCash(50, 20); dispenser.loadCash(20, 30); dispenser.loadCash(10, 50);
        ATMContext atm = new ATMContext(dispenser);
        Account acc1 = new Account("A1", "Alice", 5000);
        Card card1 = new Card("1234567890123456", "1234", "A1");
        atm.registerAccount(acc1, card1);

        System.out.println("═══ SCENARIO 1: Normal Withdrawal ═══\n");
        System.out.println(dispenser);
        atm.insertCard(card1);
        atm.enterPin("1234");
        atm.checkBalance();
        atm.withdraw(270);
        atm.checkBalance();
        atm.ejectCard();

        System.out.println("\n═══ SCENARIO 2: Wrong PIN ═══\n");
        atm.insertCard(card1);
        atm.enterPin("0000");
        atm.enterPin("9999");
        atm.enterPin("1234");
        atm.withdraw(100);
        atm.ejectCard();

        System.out.println("\n═══ SCENARIO 3: Insufficient Balance ═══\n");
        atm.insertCard(card1);
        atm.enterPin("1234");
        atm.withdraw(50000);
        atm.ejectCard();

        System.out.println("\n═══ SCENARIO 4: Deposit ═══\n");
        atm.insertCard(card1);
        atm.enterPin("1234");
        atm.deposit(500);
        atm.checkBalance();
        atm.ejectCard();

        System.out.println("\n═══ SCENARIO 5: Operations without card ═══\n");
        atm.withdraw(100);
        atm.checkBalance();
        atm.ejectCard();

        System.out.println("\n╔════════════════════════════════╗");
        System.out.println("║    ✅ ALL SCENARIOS COMPLETE    ║");
        System.out.println("╚════════════════════════════════╝");
    }
}
