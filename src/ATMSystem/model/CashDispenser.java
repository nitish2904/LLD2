package ATMSystem.model;
import java.util.*;
public class CashDispenser {
    private final TreeMap<Integer, Integer> denominations;
    public CashDispenser() { denominations = new TreeMap<>(Collections.reverseOrder()); }
    public void loadCash(int denomination, int count) { denominations.merge(denomination, count, Integer::sum); }
    public synchronized Map<Integer, Integer> dispense(double amount) {
        int remaining = (int) amount;
        Map<Integer, Integer> dispensed = new LinkedHashMap<>();
        for (Map.Entry<Integer, Integer> entry : denominations.entrySet()) {
            int denom = entry.getKey(); int available = entry.getValue();
            int needed = remaining / denom;
            int used = Math.min(needed, available);
            if (used > 0) { dispensed.put(denom, used); remaining -= denom * used; entry.setValue(available - used); }
        }
        if (remaining > 0) { dispensed.forEach((d, c) -> denominations.merge(d, c, Integer::sum)); return null; }
        return dispensed;
    }
    public double getTotalCash() { return denominations.entrySet().stream().mapToDouble(e -> (double) e.getKey() * e.getValue()).sum(); }
    @Override public String toString() { return "CashDispenser" + denominations + " total=$" + String.format("%.0f", getTotalCash()); }
}
